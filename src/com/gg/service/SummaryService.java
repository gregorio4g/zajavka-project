package com.gg.service;

import com.gg.model.Rate;
import com.gg.model.Summary;

import java.util.List;

public interface SummaryService {

	Summary calculate(List<Rate> rates);
}
