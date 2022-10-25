package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;

public interface AmountsCalculationService {

	RateAmounts calculate(InputData inputData);

	RateAmounts calculate(InputData inputData, Rate previousRate);
}
