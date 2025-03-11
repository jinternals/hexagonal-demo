package com.jinternals.demo.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void shouldCreateOrderSuccessfully() {
        OrderId orderId = new OrderId("123");
        LineItems lineItems = new LineItems(List.of(new LineItem(ProductId.of("uuid-1"), new Quantity(valueOf(10), Unit.EACH), new Amount(valueOf(10), Currency.EUR))));
        Amount totalPrice = new Amount(new BigDecimal(100), Currency.GBP);

        Order order = Order.create(orderId, lineItems, totalPrice);

        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
        assertEquals(lineItems, order.getLineItems());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(totalPrice, order.getTotalPrice());
    }

    @Test
    void shouldCancelOrderSuccessfully() {
        OrderId orderId = new OrderId("123");
        LineItems lineItems = new LineItems(List.of(new LineItem(ProductId.of("uuid-1"), new Quantity(valueOf(10), Unit.EACH), new Amount(valueOf(10), Currency.EUR))));
        Amount totalPrice = new Amount(new BigDecimal(100), Currency.GBP);
        Order order = Order.create(orderId, lineItems, totalPrice);

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenCancellingAlreadyCancelledOrder() {
        OrderId orderId = new OrderId("123");
        LineItems lineItems = new LineItems(List.of(new LineItem(ProductId.of("uuid-1"), new Quantity(valueOf(10), Unit.EACH), new Amount(valueOf(10), Currency.EUR))));
        Amount totalPrice = new Amount(new BigDecimal(100), Currency.GBP);
        Order order = Order.create(orderId, lineItems, totalPrice);

        order.cancel();

        IllegalStateException exception = assertThrows(IllegalStateException.class, order::cancel);
        assertTrue(exception.getMessage().contains("has already been cancelled"));
    }
}