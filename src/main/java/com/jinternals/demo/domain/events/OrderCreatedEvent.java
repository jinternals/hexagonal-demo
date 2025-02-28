package com.jinternals.demo.domain.events;


import com.jinternals.demo.domain.model.OrderId;
import com.jinternals.demo.domain.model.OrderStatus;

import java.time.Instant;

import static com.jinternals.demo.domain.model.OrderStatus.CREATED;

public record OrderCreatedEvent(OrderId orderId, OrderStatus status, Instant createdAt) implements DomainEvent {

    @Override
    public Instant timestamp() {
        return createdAt;
    }

    @Override
    public String getEventType() {
        return OrderCreatedEvent.class.getSimpleName();
    }

    public OrderCreatedEvent(OrderId orderId) {
        this(orderId, CREATED, Instant.now());
    }

}
