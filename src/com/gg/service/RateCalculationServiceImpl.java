package com.gg.service;

import com.gg.model.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImpl implements RateCalculationService {

	private final TimePointService timePointService;

	private final	AmountsCalculationService amountsCalculationService;

	private final	OverpaymentCalculationService overpaymentCalculationService;

	private final ResidualCalculationService residualCalculationService;

	private final ReferenceCalculationService referenceCalculationService;

	public RateCalculationServiceImpl(
			TimePointService timePointService,
			AmountsCalculationService amountsCalculationService,
			OverpaymentCalculationService overpaymentCalculationService,
			ResidualCalculationService residualCalculationService,
			ReferenceCalculationService referenceCalculationService
	) {
		this.timePointService = timePointService;
		this.amountsCalculationService = amountsCalculationService;
		this.overpaymentCalculationService = overpaymentCalculationService;
		this.residualCalculationService = residualCalculationService;
		this.referenceCalculationService = referenceCalculationService;
	}

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

			if(mortgageFinished(nextRate)) {
				break;
			}

		}

		return rates;
	}

	private static boolean mortgageFinished(@NotNull Rate nextRate) {
		BigDecimal toCompare = nextRate.getMortgageResidual().getAmount().setScale(2, RoundingMode.HALF_UP);
		return 0 == toCompare.intValue();
	}

	private @NotNull Rate calculateRate(BigDecimal rateNumber, InputData inputData) {
		TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
		Overpayment overpayment = overpaymentCalculationService.calculate(rateNumber, inputData);
		RateAmounts rateAmounts = amountsCalculationService.calculate(inputData, overpayment);
		MortgageResidual mortgageResidual = residualCalculationService.calculate(rateAmounts, inputData);
		MortgageReference mortgageReference = referenceCalculationService.calculate(inputData);

		return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidual, mortgageReference);
	}

	private Rate calculateRate(BigDecimal rateNumber, InputData inputData, Rate previousRate) {
		TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
		Overpayment overpayment = overpaymentCalculationService.calculate(rateNumber, inputData);
		RateAmounts rateAmounts = amountsCalculationService.calculate(inputData, overpayment, previousRate);
		MortgageResidual mortgageResidual = residualCalculationService.calculate(rateAmounts, previousRate);
		MortgageReference mortgageReference = referenceCalculationService.calculate(inputData);

		return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidual, mortgageReference);
	}
}
