package com.jinternals.demo.application.service;

import com.jinternals.demo.application.port.in.CancelOrderUseCase;
import com.jinternals.demo.application.port.in.CreateOrderUseCase;
import com.jinternals.demo.application.port.in.GetOrderUseCase;

public interface OrderService extends CreateOrderUseCase, GetOrderUseCase, CancelOrderUseCase {
}
