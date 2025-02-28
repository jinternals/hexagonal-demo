package com.jinternals.demo.domain.model;


import lombok.Getter;

import java.util.Objects;

@Getter
public class LineItem {
    private ProductId productId;
    private Quantity quantity;
    private Amount price;

    public LineItem(ProductId productId, Quantity quantity, Amount price) {
        this.productId = Objects.requireNonNull(productId);
        this.quantity = Objects.requireNonNull(quantity);
        this.price = Objects.requireNonNull(price);
    }

}

