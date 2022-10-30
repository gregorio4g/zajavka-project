package com.gg.model;

import java.math.BigDecimal;

public record RateAmounts(BigDecimal rateAmount, BigDecimal interestAmount, BigDecimal capitalAmount,
													Overpayment overpayment) {


}
