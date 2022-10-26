package com.gg.service;

import com.gg.model.Rate;
import com.gg.model.Summary;

import java.math.BigDecimal;
import java.util.List;

public class SummaryServiceFactory {

	public static SummaryService create() {
		return rates -> {
			BigDecimal interestSum = calculate(
					rates,
					rate -> rate.getRateAmounts().getInterestAmount()
			);
			BigDecimal provisions = calculate(
					rates,
					rate -> rate.getRateAmounts().getOverpayment().getProvisionAmount()
			);
			BigDecimal totalLosts = interestSum.add(provisions);
			return new Summary(interestSum, provisions, totalLosts);
		};
	}

	private static BigDecimal calculate(List<Rate> rates, Function function) {
		BigDecimal sum = BigDecimal.ZERO;
		for (Rate rate : rates) {
			sum = sum.add(function.calculate(rate));
		}
		return sum;
	}


}
