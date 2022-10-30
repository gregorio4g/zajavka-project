package com.gg.model;

import java.math.BigDecimal;

public record Overpayment(BigDecimal amount, BigDecimal provisionAmount) {

	public static final String REDUCE_RATE = "REDUCE_RATE";

	public static final String REDUCE_PERIOD = "REDUCE_PERIOD";


}
