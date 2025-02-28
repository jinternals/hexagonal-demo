package com.jinternals.demo.infrastructure.adapter.in.rest;

import com.jinternals.demo.application.port.in.CancelOrderUseCase;
import com.jinternals.demo.application.port.in.CreateOrderUseCase;
import com.jinternals.demo.application.port.in.GetOrderUseCase;
import com.jinternals.demo.domain.model.Order;
import com.jinternals.demo.domain.model.OrderId;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.jinternals.demo.infrastructure.adapter.in.rest.mappers.LineItemDTOMapper.INSTANCE;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @PostMapping
    public Order createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return createOrderUseCase.createOrder(createOrderRequest.getLineItems().stream().map(INSTANCE::toDomain).toList());
    }

    @GetMapping("/{orderId}")
    public Order getOrders(@PathVariable("orderId") String orderId) {
        return getOrderUseCase.getOrderById(new OrderId(orderId));
    }

    @DeleteMapping("/{orderId}/cancel")
    public Order cancelOrder(@PathVariable("orderId") String orderId) {
        return cancelOrderUseCase.cancelOrder(new OrderId(orderId));
    }

}