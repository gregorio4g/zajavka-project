package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.MortgageResidual;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ResidualCalculationServiceImpl extends ResidualCalculationCommon implements ResidualCalculationService {

	@Override
	public MortgageResidual calculate(RateAmounts rateAmounts, @NotNull InputData inputData) {
		BigDecimal residualAmount = calculateResidualAmount(inputData.getAmount(), rateAmounts);
		BigDecimal residualDuration = inputData.getMonthsDuration().subtract(BigDecimal.ONE);

		return new MortgageResidual(residualAmount, residualDuration);
	}

	@Override
	public MortgageResidual calculate(RateAmounts rateAmounts, @NotNull Rate previousRate) {
		MortgageResidual residual = previousRate.mortgageResidual();

		BigDecimal residualAmount = calculateResidualAmount(residual.amount(), rateAmounts);
		BigDecimal residualDuration = residual.duration().subtract(BigDecimal.ONE);

		return new MortgageResidual(residualAmount, residualDuration);
	}

}
