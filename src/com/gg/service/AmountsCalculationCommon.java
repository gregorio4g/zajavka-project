package com.gg.service;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AmountsCalculationCommon {

	static final BigDecimal YEAR = BigDecimal.valueOf(12);

	static @NotNull BigDecimal calculateInterestAmount(@NotNull BigDecimal residualAmount, BigDecimal interestPercent) {
		return residualAmount.multiply(interestPercent).divide(YEAR, 10, RoundingMode.HALF_UP);
	}

	@Contract(pure = true)
	static BigDecimal getCapitalAmount(BigDecimal residualAmount, BigDecimal capitalAmount) {
		if (capitalAmount.compareTo(residualAmount) > 0) {
			return residualAmount;
		} else {
			return capitalAmount;
		}
	}
}
