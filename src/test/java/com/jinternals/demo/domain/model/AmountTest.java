package com.jinternals.demo.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AmountTest {
    @Test
    void shouldCreateAmountSuccessfully() {
        Amount amount = new Amount(BigDecimal.valueOf(100), Currency.USD);

        assertNotNull(amount);
        assertEquals(BigDecimal.valueOf(100), amount.value());
        assertEquals(Currency.USD, amount.currency());
    }

    @Test
    void shouldThrowExceptionForNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Amount(BigDecimal.valueOf(-10), Currency.USD));
        assertEquals("Amount must be non-negative", exception.getMessage());
    }

    @Test
    void shouldConvertAmountToDifferentCurrency() {
        Amount amount = new Amount(BigDecimal.valueOf(100), Currency.USD);
        BigDecimal exchangeRate = BigDecimal.valueOf(0.85);
        Amount convertedAmount = amount.convertTo(Currency.EUR, exchangeRate);

        assertNotNull(convertedAmount);
        assertEquals(BigDecimal.valueOf(85.00).setScale(2), convertedAmount.value());
        assertEquals(Currency.EUR, convertedAmount.currency());
    }

    @Test
    void shouldThrowExceptionForZeroExchangeRate() {
        Amount amount = new Amount(BigDecimal.valueOf(100), Currency.USD);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> amount.convertTo(Currency.EUR, BigDecimal.ZERO));
        assertEquals("Exchange rate must be positive", exception.getMessage());
    }
}