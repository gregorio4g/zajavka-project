package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.MortgageReference;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;
import org.jetbrains.annotations.NotNull;

public interface ReferenceCalculationService {

	MortgageReference calculate(InputData inputData);

	MortgageReference calculate(@NotNull InputData inputData, RateAmounts rateAmounts, Rate previousRate);
}
