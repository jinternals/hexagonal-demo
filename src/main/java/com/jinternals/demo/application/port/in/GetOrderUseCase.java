package com.jinternals.demo.application.port.in;

import com.jinternals.demo.domain.model.Order;
import com.jinternals.demo.domain.model.OrderId;

public interface GetOrderUseCase {
    Order getOrderById(OrderId orderId);
}
