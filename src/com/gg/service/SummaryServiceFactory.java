package com.gg.service;

import com.gg.model.Rate;
import com.gg.model.Summary;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class SummaryServiceFactory {

	@Contract(pure = true)
	public static @NotNull SummaryService create() {
		return rates -> {
			BigDecimal interestSum = calculate(
					rates,
					rate -> rate.rateAmounts().interestAmount()
			);
			BigDecimal provisions = calculate(
					rates,
					rate -> rate.rateAmounts().overpayment().provisionAmount()
			);
			BigDecimal totalLost = interestSum.add(provisions);
			return new Summary(interestSum, provisions, totalLost);
		};
	}

	private static BigDecimal calculate(@NotNull List<Rate> rates, Function function) {
		BigDecimal sum = BigDecimal.ZERO;
		for (Rate rate : rates) {
			sum = sum.add(function.calculate(rate));
		}
		return sum;
	}


}
