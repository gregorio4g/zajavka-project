package com.gg.service;

import com.gg.model.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ReferenceCalculationServiceImpl extends ResidualCalculationCommon implements ReferenceCalculationService {

	@Override
	public MortgageReference calculate(@NotNull InputData inputData) {
		return new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
	}

	@Override
	public MortgageReference calculate(@NotNull InputData inputData, RateAmounts rateAmounts, @NotNull Rate previousRate) {
		if (BigDecimal.ZERO.equals(previousRate.mortgageResidual().amount()))	{
			return new MortgageReference(BigDecimal.ZERO, BigDecimal.ZERO);
		}

		return switch (inputData.getOverpaymentReduceWay()) {
			case Overpayment.REDUCE_PERIOD -> new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
			case Overpayment.REDUCE_RATE -> reduceRateMortgageReference(rateAmounts, previousRate);
			default -> throw new MortgageException();
		};


	}

	@Contract("_, _ -> new")
	private @NotNull MortgageReference reduceRateMortgageReference(@NotNull RateAmounts rateAmounts, @NotNull Rate previousRate) {
		if (rateAmounts.overpayment().amount().compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal referenceAmount =
					calculateResidualAmount(previousRate.mortgageResidual().amount(), rateAmounts);
			BigDecimal referenceDuration = previousRate.mortgageResidual().duration().subtract(BigDecimal.ONE);
			return new MortgageReference(referenceAmount, referenceDuration);
		} else {
			return new MortgageReference(
					previousRate.mortgageReference().getAmount(),
					previousRate.mortgageReference().getDuration()
			);
		}
	}
}
