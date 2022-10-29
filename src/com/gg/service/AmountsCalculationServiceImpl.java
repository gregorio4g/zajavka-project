package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Overpayment;
import com.gg.model.Rate;
import com.gg.model.RateAmounts;
import org.jetbrains.annotations.NotNull;

public class AmountsCalculationServiceImpl implements AmountsCalculationService {

	private final	ConstantAmountsCalculationServiceImpl constantAmountsCalculationService;

	private final DecreasingAmountsCalculationServiceImpl decreasingAmountsCalculationService;

	public AmountsCalculationServiceImpl(
			ConstantAmountsCalculationServiceImpl constantAmountsCalculationService,
			DecreasingAmountsCalculationServiceImpl decreasingAmountsCalculationService
	) {
		this.constantAmountsCalculationService = constantAmountsCalculationService;
		this.decreasingAmountsCalculationService = decreasingAmountsCalculationService;
	}

	@Override
	public RateAmounts calculate(@NotNull InputData inputData, Overpayment overpayment) {
		return switch (inputData.getRateType()) {
			case CONSTANT -> constantAmountsCalculationService.calculate(inputData, overpayment);
			case DECREASING -> decreasingAmountsCalculationService.calculate(inputData, overpayment);
		};
	}

	@Override
	public RateAmounts calculate(@NotNull InputData inputData, Overpayment overpayment, Rate previousRate) {
		return switch (inputData.getRateType()) {
			case CONSTANT -> constantAmountsCalculationService.calculate(inputData, overpayment, previousRate);
			case DECREASING -> decreasingAmountsCalculationService.calculate(inputData, overpayment, previousRate);
		};
	}


}
