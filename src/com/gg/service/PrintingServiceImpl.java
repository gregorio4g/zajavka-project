package com.gg.service;

import com.gg.model.InputData;
import com.gg.model.Overpayment;
import com.gg.model.Rate;
import com.gg.model.Summary;

import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class PrintingServiceImpl implements PrintingService {

	@Override
	public void printInputDataInfo(InputData inputData) {
		StringBuilder msg = new StringBuilder(NEW_LINE);
		msg.append(MORTGAGE_AMOUNT).append(inputData.getAmount()).append(CURRENCY);
		msg.append(NEW_LINE);
		msg.append(MORTGAGE_PERIOD).append(inputData.getMonthsDuration()).append(MONTHS);
		msg.append(NEW_LINE);
		msg.append(INTEREST).append(inputData.getInterestDisplay()).append(PERCENT);
		msg.append(NEW_LINE);

		Optional.of(inputData.getOverpaymentSchema())
						.filter(schema -> schema.size() > 0)
								.ifPresent(schema -> logOverpayment(msg, inputData));

		printMessage(msg.toString());
	}

	private void logOverpayment(StringBuilder msg, InputData inputData) {
		switch (inputData.getOverpaymentReduceWay()) {
			case Overpayment.REDUCE_PERIOD -> msg.append(OVERPAYMENT_REDUCE_PERIOD);
			case Overpayment.REDUCE_RATE -> msg.append(OVERPAYMENT_REDUCE_RATE);
			default -> throw new MortgageException();
		}

		msg.append(NEW_LINE);
		msg.append(OVERPAYMENT_FREQUENCY).append(inputData.getOverpaymentSchema());
		msg.append(NEW_LINE);
	}

	@Override
	public void printRates(List<Rate> rates) {
		String format = "%s%3.0f  " + // RATE_NUMBER
				"%s%s  " +								// DATE
				"%s%2.0f  " +							// YEAR
				"%s%2.0f  " +							// MONTH
				"%s%7.2f  " +							// RATE
				"%s%6.2f  " +							// INTEREST
				"%s%7.2f  " +							// CAPITAL
				"%s%9.2f  " +							// OVERPAYMENT
				"%s%9.2f  " +							// LEFT_AMOUNT
				"%s%3.0f";								// LEFT_MONTH

		for (Rate rate : rates) {
			String message = String.format(format,
					RATE_NUMBER, rate.getRateNumber(),
					DATE, rate.getTimePoint().getDate(),
					YEAR, rate.getTimePoint().getYear(),
					MONTH, rate.getTimePoint().getMonth(),
					RATE, rate.getRateAmounts().getRateAmount(),
					INTEREST, rate.getRateAmounts().getInterestAmount(),
					CAPITAL, rate.getRateAmounts().getCapitalAmount(),
					OVERPAYMENT, rate.getRateAmounts().getOverpayment().getAmount(),
					LEFT_AMOUNT, rate.getMortgageResidual().getAmount(),
					LEFT_MONTH, rate.getMortgageResidual().getDuration()
			);
			printMessage(message);

			if (rate.getRateNumber().intValue() % 12 == 0) {
				System.out.println();
			}
		}
	}

	@Override
	@SuppressWarnings("StringBufferReplaceableByString")
	public void printSummary(Summary summary) {
		StringBuilder msg = new StringBuilder(NEW_LINE);
		msg.append(INTEREST_SUM).append(summary.getInterestSum().setScale(2, RoundingMode.HALF_UP)).append(CURRENCY);
		msg.append(NEW_LINE);
		msg.append(OVERPAYMENT_PROVISION).append(summary.getOverpaymentProvisions().setScale(2, RoundingMode.HALF_UP)).append(CURRENCY);
		msg.append(NEW_LINE);
		msg.append(LOSTS_SUM).append(summary.getTotalLosts().setScale(2, RoundingMode.HALF_UP)).append(CURRENCY);
		msg.append(NEW_LINE);

		printMessage(msg.toString());

	}

	private void printMessage(String s) {
		System.out.println(s);
	}
}
