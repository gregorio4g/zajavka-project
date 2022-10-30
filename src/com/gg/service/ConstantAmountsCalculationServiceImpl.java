package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Overpayment;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConstantAmountsCalculationServiceImpl extends AmountsCalculationCommon implements ConstantAmountsCalculationService {

	@Override
	public RateAmounts calculate(@NotNull InputData inputData, Overpayment overpayment) {
		BigDecimal interestPercent = inputData.getInterestPercent();
		BigDecimal q = calculateQ(interestPercent);

		BigDecimal residualAmount = inputData.getAmount();
		BigDecimal referenceAmount = inputData.getAmount();
		BigDecimal referenceDuration = inputData.getMonthsDuration();

		BigDecimal interestAmount = calculateInterestAmount(referenceAmount, interestPercent);
		BigDecimal rateAmount = calculateRateAmount(
				q, referenceAmount, referenceDuration, interestAmount, residualAmount);
		BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

		return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
	}

	@Override
	public RateAmounts calculate(@NotNull InputData inputData, Overpayment overpayment, @NotNull Rate previousRate) {
		BigDecimal interestPercent = inputData.getInterestPercent();
		BigDecimal q = calculateQ(interestPercent);

		BigDecimal residualAmount = previousRate.mortgageResidual().amount();
		BigDecimal referenceAmount = previousRate.mortgageReference().getAmount();
		BigDecimal referenceDuration = previousRate.mortgageReference().getDuration();

		BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
		BigDecimal rateAmount = calculateRateAmount(
				q, referenceAmount, referenceDuration, interestAmount, residualAmount);
		BigDecimal capitalAmount = calculateCapitalAmount(rateAmount, interestAmount, residualAmount);

		return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
	}

	private @NotNull BigDecimal calculateQ(@NotNull BigDecimal interestPercent) {
		return interestPercent.divide(YEAR, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
	}

	private @NotNull BigDecimal calculateRateAmount(
			@NotNull BigDecimal q,
			BigDecimal amount,
			BigDecimal monthsDuration,
			BigDecimal interestAmount,
			BigDecimal residualAmount
	) {
		BigDecimal rateAmount = amount
				.multiply(q.pow(monthsDuration.intValue()))
				.multiply(q.subtract(BigDecimal.ONE))
				.divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);

		return compareWithResidual(rateAmount, interestAmount, residualAmount);
	}

	private BigDecimal compareWithResidual(BigDecimal rateAmount, BigDecimal interestAmount, BigDecimal residualAmount) {
		if (rateAmount.subtract(interestAmount).compareTo(residualAmount) > 0) {
			return residualAmount.add(interestAmount);
		} else {
			return rateAmount;
		}
	}

	@Contract(pure = true)
	private @NotNull BigDecimal calculateCapitalAmount(
			@NotNull BigDecimal rateAmount,
			BigDecimal interestAmount,
			BigDecimal residualAmount) {
		BigDecimal capitalAmount = rateAmount.subtract(interestAmount);

		return getCapitalAmount(residualAmount, capitalAmount);
	}

}
