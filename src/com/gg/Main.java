package com.gg;

import com.gg.model.*;
import com.gg.service.*;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		InputData inputData = new InputData()
				.withAmount(BigDecimal.valueOf(298000))
				.withMonthsDuration(BigDecimal.valueOf(360))
				.withRateType(RateType.CONSTANT);

		PrintingService printingService = new PrintingServiceImpl();
		RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
				new TimePointServiceImpl(),
				new AmountsCalculationServiceImpl(),
				new ResidualCalculationServiceImpl()
		);

		MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
				printingService,
				rateCalculationService,
				SummaryServiceFactory.create()
		);
		mortgageCalculationService.calculate(inputData);
	}
}