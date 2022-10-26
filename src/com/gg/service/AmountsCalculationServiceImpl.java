package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Overpayment;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountsCalculationServiceImpl implements AmountsCalculationService {

	public static final BigDecimal YEAR = BigDecimal.valueOf(12);

	@Override
	public RateAmounts calculate(InputData inputData, Overpayment overpayment) {
		return switch (inputData.getRateType()) {
			case CONSTANT -> calculateConstantRate(inputData, overpayment);
			case DECREASING -> calculateDecreasingRate(inputData, overpayment);
		};
	}

	@Override
	public RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate) {
		return switch (inputData.getRateType()) {
			case CONSTANT -> calculateConstantRate(inputData, overpayment, previousRate);
			case DECREASING -> calculateDecreasingRate(inputData, overpayment, previousRate);
		};
	}

	private RateAmounts calculateConstantRate(InputData inputData, Overpayment overpayment) {
		BigDecimal interestPercent = inputData.getInterestPercent();
		BigDecimal residualAmount = inputData.getAmount();

		BigDecimal q = calculateQ(interestPercent);

		BigDecimal rateAmount = calculateConstantRateAmount(q, inputData.getAmount(), inputData.getMonthsDuration());
		BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
		BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

		return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
	}

	private RateAmounts calculateConstantRate(InputData inputData, Overpayment overpayment, Rate previousRate) {
		BigDecimal interestPercent = inputData.getInterestPercent();
		BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

		BigDecimal q = calculateQ(interestPercent);

		BigDecimal rateAmount = calculateConstantRateAmount(q, inputData.getAmount(), inputData.getMonthsDuration());
		BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
		BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

		return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
	}

	private RateAmounts calculateDecreasingRate(InputData inputData, Overpayment overpayment) {
		BigDecimal interestPercent = inputData.getInterestPercent();
		BigDecimal residualAmount = inputData.getAmount();

		BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
		BigDecimal capitalAmount = calculateDecreasingCapitalAmount(residualAmount, inputData.getMonthsDuration());
		BigDecimal rateAmount = capitalAmount.add(interestAmount);

		return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
	}

	private RateAmounts calculateDecreasingRate(InputData inputData, Overpayment overpayment, Rate previousRate) {
		BigDecimal interestPercent = inputData.getInterestPercent();
		BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

		BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
		BigDecimal capitalAmount = calculateDecreasingCapitalAmount(inputData.getAmount(), inputData.getMonthsDuration());
		BigDecimal rateAmount = capitalAmount.add(interestAmount);

		return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
	}

	private BigDecimal calculateQ(BigDecimal interestPercent) {
		return interestPercent.divide(YEAR, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
	}

	private BigDecimal calculateConstantRateAmount(BigDecimal q, BigDecimal amount, BigDecimal monthsDuration) {
		return amount
				.multiply(q.pow(monthsDuration.intValue()))
				.multiply(q.subtract(BigDecimal.ONE))
				.divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);
	}

	private BigDecimal calculateConstantCapitalAmount(BigDecimal rateAmount, BigDecimal interestAmount) {
		return rateAmount.subtract(interestAmount);
	}

	private BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent) {
		return residualAmount.multiply(interestPercent).divide(YEAR, 10, RoundingMode.HALF_UP);
	}

	private BigDecimal calculateDecreasingCapitalAmount(BigDecimal amount, BigDecimal monthsDuration) {
		return amount.divide(monthsDuration, 10, RoundingMode.HALF_UP);
	}
}
