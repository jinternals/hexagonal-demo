package com.jinternals.demo.domain.exceptions;

import com.jinternals.demo.domain.model.OrderId;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(OrderId orderId) {
        super("Order not found: " + orderId);
    }
}
