package com.jinternals.demo.application.port.in;

import com.jinternals.demo.domain.model.LineItem;
import com.jinternals.demo.domain.model.Order;

import java.util.List;

public interface CreateOrderUseCase {
    Order createOrder(List<LineItem> lineItems);
}
