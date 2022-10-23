package com.gg;

import com.gg.model.InputData;
import com.gg.service.*;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		InputData inputData = new InputData()
				.withAmount(BigDecimal.valueOf(298000))
				.withMonthsDuration(BigDecimal.valueOf(160));

		PrintingService printingService = new PrintingServiceImpl();
		RateCalculationService rateCalculationService = new RateCalculationServiceImpl();

		MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
				printingService,
				rateCalculationService
		);
		mortgageCalculationService.calculate(inputData);
	}
}