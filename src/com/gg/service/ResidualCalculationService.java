package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.MortgageResidual;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;

public interface ResidualCalculationService {

	MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData);

	MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate);
}
