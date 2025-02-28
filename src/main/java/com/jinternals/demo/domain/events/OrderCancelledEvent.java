package com.jinternals.demo.domain.events;


import com.jinternals.demo.domain.model.OrderId;
import com.jinternals.demo.domain.model.OrderStatus;

import java.time.Instant;

import static com.jinternals.demo.domain.model.OrderStatus.CANCELLED;

public record OrderCancelledEvent(OrderId orderId, OrderStatus status, Instant createdAt) implements DomainEvent {

    @Override
    public Instant timestamp() {
        return createdAt;
    }

    @Override
    public String getEventType() {
        return OrderCancelledEvent.class.getSimpleName();
    }

    public OrderCancelledEvent(OrderId orderId) {
        this(orderId, CANCELLED, Instant.now());
    }

}
