package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.TimePoint;

import java.math.BigDecimal;

public interface TimePointService {

	TimePoint calculate(BigDecimal rateNumber, InputData inputData);
}
