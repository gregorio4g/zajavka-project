package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.TimePoint;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TimePointServiceImpl implements TimePointService {

	public static final BigDecimal YEAR = BigDecimal.valueOf(12);

	@Override
	public TimePoint calculate(BigDecimal rateNumber, InputData inputData) {
		LocalDate date = calculateDate(rateNumber, inputData);
		BigDecimal year = calculateYear(rateNumber);
		BigDecimal month = calculateMonth(rateNumber);
		return new TimePoint(date, year, month);
	}

	private LocalDate calculateDate(@NotNull BigDecimal rateNumber, @NotNull InputData inputData) {
		return inputData.getRepaymentStartDate()
				.plus(rateNumber.subtract(BigDecimal.ONE).intValue(), ChronoUnit.MONTHS);
	}

	private @NotNull BigDecimal calculateYear(final @NotNull BigDecimal rateNumber) {
		return rateNumber.divide(YEAR, RoundingMode.UP).max(BigDecimal.ONE);
	}

	private BigDecimal calculateMonth(final @NotNull BigDecimal rateNumber) {
		return BigDecimal.ZERO.equals(rateNumber.remainder(YEAR)) ? YEAR : rateNumber.remainder(YEAR);
	}
}
