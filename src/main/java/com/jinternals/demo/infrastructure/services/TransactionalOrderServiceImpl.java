package com.jinternals.demo.infrastructure.services;

import com.jinternals.demo.application.service.OrderService;
import com.jinternals.demo.domain.model.LineItem;
import com.jinternals.demo.domain.model.Order;
import com.jinternals.demo.domain.model.OrderId;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TransactionalOrderServiceImpl implements OrderService {
    private final OrderService delegate;

    public TransactionalOrderServiceImpl(OrderService delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public Order createOrder(List<LineItem> items) {
        return delegate.createOrder(items);
    }

    @Override
    public Order getOrderById(OrderId orderId) {
        return delegate.getOrderById(orderId);
    }

    @Override
    @Transactional
    public Order cancelOrder(OrderId orderId) {
        return delegate.cancelOrder(orderId);
    }
}

