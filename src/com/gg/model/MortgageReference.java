package com.gg.model;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class MortgageReference {

	private final @NotNull BigDecimal referenceAmount;

	private final @NotNull BigDecimal referenceDuration;

	public MortgageReference(@NotNull BigDecimal referenceAmount, @NotNull BigDecimal referenceDuration) {
		this.referenceAmount = referenceAmount;
		this.referenceDuration = referenceDuration;
	}

	public @NotNull BigDecimal getAmount() {
		return referenceAmount;
	}

	public BigDecimal getDuration() {
		return referenceDuration;
	}
}
