package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Rate;
import com.gg.model.TimePoint;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImpl implements RateCalculationService {

	private final TimePointService timePointService;

	private final	AmountsCalculationService amountsCalculationService;

	private final ResidualCalculationService residualCalculationService;

	@Override
	public List<Rate> calculate(InputData inputData) {
		List<Rate> rates = new LinkedList<>();

		BigDecimal rateNumber = BigDecimal.ONE;

		Rate firstRate = calculateRate(rateNumber, inputData);
		rates.add(firstRate);

		Rate previousRate = firstRate;

		for (
				BigDecimal index = rateNumber.add(BigDecimal.ONE);
				index.compareTo(inputData.getMonthsDuration()) <= 0;
				index = index.add(BigDecimal.ONE)
		) {
			Rate nextRate = calculateRate(index, inputData, previousRate);
			rates.add(nextRate);
			previousRate = nextRate;

		}

		return rates;
	}

	private Rate calculateRate(BigDecimal rateNumber, InputData inputData) {
		TimePoint timePoint = timePointService.calculate();

		return new Rate();
	}

	private Rate calculateRate(BigDecimal rateNumber, InputData inputData, Rate previousRate) {
		return new Rate();
	}
}
