package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.MortgageReference;

public interface ReferenceCalculationService {

	MortgageReference calculate(InputData inputData);
}
