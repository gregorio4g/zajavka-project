package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Rate;
import com.gg.model.Summary;

import java.util.List;

public interface PrintingService {

	String INTEREST_SUM = "SUMA ODSETEK: ";
	String RATE_NUMBER = "NR: ";
	String YEAR = "ROK: ";
	String MONTH = "MIESIAC: ";
	String DATE = "DATA: ";
	String MONTHS = " MIESIECY";
	String RATE = "RATA: ";
	String INTEREST = "ODSETKI: ";
	String CAPITAL = "KAPITAL: ";
	String LEFT_AMOUNT = "PKWOTA: ";
	String LEFT_MONTH = "PMSC: ";
	String MORTGAGE_AMOUNT = "KWOTA KREDYTU: ";
	String MORTGAGE_PERIOD = "OKRES KREDYTOWANIA: ";

	String CURRENCY = " ZL ";
	String NEW_LINE = "\n";
	String PERCENT = "%";

	void printInputDataInfo(final InputData inputData);

	void printRates(List<Rate> rates);

	void printSummary(Summary summary);
}
