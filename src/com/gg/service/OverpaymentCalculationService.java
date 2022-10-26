package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Overpayment;

import java.math.BigDecimal;

public interface OverpaymentCalculationService {

	Overpayment calculate(BigDecimal rateNumber, InputData inputData);
}
