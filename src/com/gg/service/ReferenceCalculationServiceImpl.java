package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.MortgageReference;
import org.jetbrains.annotations.NotNull;

public class ReferenceCalculationServiceImpl implements ReferenceCalculationService {

	@Override
	public MortgageReference calculate(@NotNull InputData inputData) {
		return new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
	}
}
