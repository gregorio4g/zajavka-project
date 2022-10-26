package com.gg.model;

import java.math.BigDecimal;

public class Summary {

	private final BigDecimal interestSum;

	private final BigDecimal overpaymentProvisions;

	private final BigDecimal totalLosts;

	public Summary(BigDecimal interestSum, BigDecimal overpaymentProvisions, BigDecimal totalLosts) {
		this.interestSum = interestSum;
		this.overpaymentProvisions = overpaymentProvisions;
		this.totalLosts = totalLosts;
	}

	public BigDecimal getInterestSum() {
		return interestSum;
	}

	public BigDecimal getOverpaymentProvisions() {
		return overpaymentProvisions;
	}

	public BigDecimal getTotalLosts() {
		return totalLosts;
	}
}
