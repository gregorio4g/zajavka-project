package com.gg;

import com.gg.model.*;
import com.gg.service.*;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		InputData inputData = new InputData()
				.withAmount(BigDecimal.valueOf(300000))
				.withMonthsDuration(BigDecimal.valueOf(360))
				.withRateType(RateType.DECREASING)
				.withOverpaymentReduceWay(Overpayment.REDUCE_RATE);

		PrintingService printingService = new PrintingServiceImpl();
		RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
				new TimePointServiceImpl(),
				new AmountsCalculationServiceImpl(
						new ConstantAmountsCalculationServiceImpl(),
						new DecreasingAmountsCalculationServiceImpl()
				),
				new OverpaymentCalculationServiceImpl(),
				new ResidualCalculationServiceImpl(),
				new ReferenceCalculationServiceImpl()
		);

		MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
				printingService,
				rateCalculationService,
				SummaryServiceFactory.create()
		);
		mortgageCalculationService.calculate(inputData);
	}
}