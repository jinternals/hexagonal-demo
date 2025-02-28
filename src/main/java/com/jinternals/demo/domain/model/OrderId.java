package com.jinternals.demo.domain.model;

import static java.util.Objects.requireNonNull;

public record OrderId(String value) {
    public OrderId {
            requireNonNull(value , "OrderId cannot be null");
    }

    public static OrderId of(String value) {
        return new OrderId(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
