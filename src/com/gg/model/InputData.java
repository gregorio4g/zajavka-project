package com.gg.model;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

public class InputData {
	private static final @NotNull BigDecimal PERCENT = BigDecimal.valueOf(100);

	private @NotNull LocalDate repaymentStartDate = LocalDate.of(2020, 1, 6);

	private @NotNull BigDecimal wiborPercent = new BigDecimal("1.73");

	private @NotNull BigDecimal amount = new BigDecimal("300000");
	private @NotNull BigDecimal monthsDuration = new BigDecimal("180");

	private @NotNull RateType rateType = RateType.DECREASING;

	private @NotNull BigDecimal bankMarginPercent = new BigDecimal("1.9");

	private @NotNull Map<Integer, BigDecimal> overpaymentSchema = Map.of(
			5, BigDecimal.valueOf(10000),
			6, BigDecimal.valueOf(10000),
			7, BigDecimal.valueOf(10000),
			8, BigDecimal.valueOf(10000)
	);
	private @NotNull String overpaymentReduceWay = Overpayment.REDUCE_PERIOD;

	private @NotNull BigDecimal overpaymentProvisionPercent = BigDecimal.valueOf(3);
	private @NotNull BigDecimal overpaymentProvisionMonths = BigDecimal.valueOf(36);

	@SuppressWarnings("unused")
	public @NotNull InputData withOverpaymentSchema(@NotNull Map<Integer, BigDecimal> overpaymentSchema) {
		this.overpaymentSchema = overpaymentSchema;
		return this;
	}

	@SuppressWarnings("unused")
	public @NotNull InputData withOverpaymentReduceWay(@NotNull String overpaymentReduceWay) {
		this.overpaymentReduceWay = overpaymentReduceWay;
		return this;
	}

	@SuppressWarnings("unused")
	public @NotNull InputData withOverpaymentProvisionPercent(@NotNull BigDecimal overpaymentProvisionPercent) {
		this.overpaymentProvisionPercent = overpaymentProvisionPercent;
		return this;
	}

	@SuppressWarnings("unused")
	public @NotNull InputData withOverpaymentProvisionMonths(@NotNull BigDecimal overpaymentProvisionMonths) {
		this.overpaymentProvisionMonths = overpaymentProvisionMonths;
		return this;
	}

	@SuppressWarnings("unused")
	public @NotNull InputData withRepaymentStartDate(@NotNull LocalDate repaymentStartDate) {
		this.repaymentStartDate = repaymentStartDate;
		return this;
	}

	@SuppressWarnings("unused")
	public @NotNull InputData withWiborPercent(@NotNull BigDecimal wiborPercent) {
		this.wiborPercent = wiborPercent;
		return this;
	}

	public @NotNull InputData withAmount(@NotNull BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public InputData withMonthsDuration(BigDecimal monthsDuration) {
		this.monthsDuration = monthsDuration;
		return this;
	}

	public InputData withRateType(RateType rateType) {
		this.rateType = rateType;
		return this;
	}

	@SuppressWarnings("unused")
	public InputData withBankMarginPercent(BigDecimal bankMarginPercent) {
		this.bankMarginPercent = bankMarginPercent;
		return this;
	}

	public LocalDate getRepaymentStartDate() {
		return repaymentStartDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getMonthsDuration() {
		return monthsDuration;
	}

	public RateType getRateType() {
		return rateType;
	}

	public BigDecimal getInterestPercent() {
		return wiborPercent.add(bankMarginPercent).divide(PERCENT, 10, RoundingMode.HALF_UP);
	}

	public BigDecimal getInterestDisplay() {
		return wiborPercent.add(bankMarginPercent).setScale(2, RoundingMode.HALF_UP);
	}

	public Map<Integer, BigDecimal> getOverpaymentSchema() {
		return overpaymentSchema;
	}

	public String getOverpaymentReduceWay() {
		return overpaymentReduceWay;
	}

	public BigDecimal getOverpaymentProvisionPercent() {
		return overpaymentProvisionPercent.divide(PERCENT,10, RoundingMode.HALF_UP);
	}

	public BigDecimal getOverpaymentProvisionMonths() {
		return overpaymentProvisionMonths;
	}
}
