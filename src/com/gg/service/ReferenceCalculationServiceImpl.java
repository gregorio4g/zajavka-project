package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.MortgageReference;

public class ReferenceCalculationServiceImpl implements ReferenceCalculationService {

	@Override
	public MortgageReference calculate(InputData inputData) {
		return new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
	}
}
