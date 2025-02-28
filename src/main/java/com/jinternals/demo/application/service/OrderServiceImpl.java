package com.jinternals.demo.application.service;

import com.jinternals.demo.application.port.out.DomainEventPublisher;
import com.jinternals.demo.application.port.out.OrderOutputPort;
import com.jinternals.demo.domain.events.DomainEvent;
import com.jinternals.demo.domain.events.OrderCreatedEvent;
import com.jinternals.demo.domain.logger.DomainLogger;
import com.jinternals.demo.domain.model.*;
import com.jinternals.demo.domain.services.IdGenerator;
import com.jinternals.demo.domain.services.OrderPricingService;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderPricingService pricingService;
    private final OrderOutputPort orderOutputPort;
    private final DomainEventPublisher domainEventPublisher;
    private final IdGenerator idGenerator;
    private final DomainLogger log;

    @Override
    public Order getOrderById(OrderId orderId) {
        log.info("Get order by id: " + orderId);
        return orderOutputPort.getOrderById(orderId);
    }

    @Override
    public Order createOrder(List<LineItem> items) {
        log.info("Create Order");
        BigDecimal exchangeRate = new BigDecimal("0.78");
        Amount totalPrice = pricingService.calculateTotalPrice(items, Currency.GBP, exchangeRate);
        Order savedOrder = orderOutputPort.save(Order.create(OrderId.of(idGenerator.generateId()), new LineItems(items), totalPrice));
        domainEventPublisher.publish(new OrderCreatedEvent(savedOrder.getOrderId()));
        return savedOrder;
    }

    @Override
    public Order cancelOrder(OrderId orderId) {
        Order order = orderOutputPort.getOrderById(orderId);
        order.cancel();
        Order updateOrder = orderOutputPort.update(order);
        domainEventPublisher.publish(new OrderCreatedEvent(updateOrder.getOrderId()));
        return updateOrder;
    }
}