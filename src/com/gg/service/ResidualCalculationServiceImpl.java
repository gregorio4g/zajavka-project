package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.MortgageResidual;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;

import java.math.BigDecimal;

public class ResidualCalculationServiceImpl implements ResidualCalculationService {

	@Override
	public MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData) {
		BigDecimal residualAmount = inputData.getAmount().subtract(rateAmounts.getCapitalAmount());
		BigDecimal residualDuration = inputData.getMonthsDuration().subtract(BigDecimal.ONE);

		return new MortgageResidual(residualAmount, residualDuration);
	}

	@Override
	public MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate) {
		MortgageResidual residual = previousRate.getMortgageResidual();

		BigDecimal residualAmount = residual.getAmount().subtract(rateAmounts.getCapitalAmount());
		BigDecimal residualDuration = residual.getDuration().subtract(BigDecimal.ONE);

		return new MortgageResidual(residualAmount, residualDuration);
	}
}
