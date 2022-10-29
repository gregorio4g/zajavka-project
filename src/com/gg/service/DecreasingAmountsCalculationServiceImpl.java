package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Overpayment;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecreasingAmountsCalculationServiceImpl extends AmountsCalculationCommon implements DecreasingAmountsCalculationService {

	@Override
	public RateAmounts calculate(@NotNull InputData inputData, Overpayment overpayment) {
		BigDecimal interestPercent = inputData.getInterestPercent();

		BigDecimal residualAmount = inputData.getAmount();
		BigDecimal referenceAmount = inputData.getAmount();
		BigDecimal referenceDuration = inputData.getMonthsDuration();

		BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
		BigDecimal capitalAmount = calculateCapitalAmount(referenceAmount, referenceDuration, residualAmount);
		BigDecimal rateAmount = capitalAmount.add(interestAmount);

		return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
	}

	@Override
	public RateAmounts calculate(@NotNull InputData inputData, Overpayment overpayment, @NotNull Rate previousRate) {
		BigDecimal interestPercent = inputData.getInterestPercent();

		BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();
		BigDecimal referenceAmount = previousRate.getMortgageReference().getAmount();
		BigDecimal referenceDuration = previousRate.getMortgageReference().getDuration();

		BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
		BigDecimal capitalAmount = calculateCapitalAmount(referenceAmount, referenceDuration, residualAmount);
		BigDecimal rateAmount = capitalAmount.add(interestAmount);

		return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
	}

	@Contract(pure = true)
	private @NotNull BigDecimal calculateCapitalAmount(
			@NotNull BigDecimal amount,
			BigDecimal monthsDuration,
			BigDecimal residualAmount
	) {
		BigDecimal capitalAmount = amount.divide(monthsDuration, 10, RoundingMode.HALF_UP);

		return getCapitalAmount(residualAmount, capitalAmount);
	}

}
