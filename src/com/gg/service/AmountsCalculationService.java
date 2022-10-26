package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Overpayment;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;

public interface AmountsCalculationService {

	RateAmounts calculate(InputData inputData, Overpayment overpayment);

	RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate);
}
