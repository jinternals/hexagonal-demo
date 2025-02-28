package com.jinternals.demo.domain.model;

import java.math.BigDecimal;

public record Quantity(BigDecimal value, Unit unit) {
    public Quantity {
        if ( value == null || value.compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
    }
}
