package com.gg.service;

import com.gg.model.RateAmounts;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public abstract class ResidualCalculationCommon {
	static @NotNull BigDecimal calculateResidualAmount(@NotNull BigDecimal amount,@NotNull RateAmounts rateAmounts) {
		return amount.subtract(rateAmounts.capitalAmount())
				.subtract(rateAmounts.overpayment().amount())
				.max(BigDecimal.ZERO);
	}
}
