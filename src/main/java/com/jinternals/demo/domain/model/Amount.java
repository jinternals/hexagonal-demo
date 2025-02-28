package com.jinternals.demo.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Amount(BigDecimal value, Currency currency) {
    public Amount {
        Objects.requireNonNull(value, "Amount value cannot be null");
        Objects.requireNonNull(currency, "Currency cannot be null");

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
    }
    public Amount convertTo(Currency targetCurrency, BigDecimal exchangeRate) {
        Objects.requireNonNull(targetCurrency, "Target currency cannot be null");
        Objects.requireNonNull(exchangeRate, "Exchange rate cannot be null");

        if (exchangeRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Exchange rate must be positive");
        }

        BigDecimal convertedValue = value.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
        return new Amount(convertedValue, targetCurrency);
    }

}
