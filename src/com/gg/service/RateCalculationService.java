package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Rate;

import java.util.List;

public interface RateCalculationService {

	List<Rate> calculate(final InputData inputData);
}
