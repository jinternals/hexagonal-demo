package com.jinternals.demo.domain.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.jinternals.demo.domain.model.OrderStatus.CANCELLED;
import static com.jinternals.demo.domain.model.OrderStatus.CREATED;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    private OrderId orderId;
    private LineItems lineItems;
    private OrderStatus status;
    private Amount totalPrice;

    public Order(OrderId orderId, LineItems lineItems, OrderStatus status, Amount totalPrice) {
        this.orderId = Objects.requireNonNull(orderId, "orderId cannot be null");
        this.lineItems = Objects.requireNonNull(lineItems, "lineItems cannot be null");
        this.status = Objects.requireNonNull(status, "status cannot be null");
        this.totalPrice = Objects.requireNonNull(totalPrice, "totalPrice cannot be null");
    }

    public static Order create(OrderId orderId, LineItems lineItems, Amount totalPrice) {
        return new Order(orderId, lineItems, CREATED, totalPrice);
    }

    public void cancel() {
        if (OrderStatus.CANCELLED.equals(this.status)) {
            throw new IllegalStateException(orderId + " has already been cancelled");
        }
        this.status = CANCELLED;
    }

}
