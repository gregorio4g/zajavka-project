package com.gg.service;

import com.gg.model.RateAmounts;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public abstract class ResidualCalculationCommon {
	static @NotNull BigDecimal calculateResidualAmount(@NotNull BigDecimal amount,@NotNull RateAmounts rateAmounts) {
		return amount.subtract(rateAmounts.getCapitalAmount())
				.subtract(rateAmounts.getOverpayment().getAmount())
				.max(BigDecimal.ZERO);
	}
}
