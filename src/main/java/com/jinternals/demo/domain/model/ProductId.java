package com.jinternals.demo.domain.model;

public record ProductId(String value) {
    public ProductId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ProductId cannot be empty");
        }
    }

    public static ProductId of(String value) {
        return new ProductId(value);
    }
}
