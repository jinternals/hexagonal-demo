package com.jinternals.demo.application.service;

import com.jinternals.demo.application.port.out.DomainEventPublisher;
import com.jinternals.demo.application.port.out.OrderOutputPort;
import com.jinternals.demo.domain.events.OrderCancelledEvent;
import com.jinternals.demo.domain.events.OrderCreatedEvent;
import com.jinternals.demo.domain.logger.DomainLogger;
import com.jinternals.demo.domain.model.*;
import com.jinternals.demo.domain.services.IdGenerator;
import com.jinternals.demo.domain.services.OrderPricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderPricingService pricingService;
    private OrderOutputPort orderOutputPort;
    private DomainEventPublisher domainEventPublisher;
    private IdGenerator idGenerator;
    private DomainLogger log;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setup() {
        pricingService = mock(OrderPricingService.class);
        orderOutputPort = mock(OrderOutputPort.class);
        domainEventPublisher = mock(DomainEventPublisher.class);
        idGenerator = mock(IdGenerator.class);
        log = mock(DomainLogger.class);

        orderService = new OrderServiceImpl(pricingService, orderOutputPort, domainEventPublisher, idGenerator, log);
    }

    @Test
    void shouldGetOrderById() {
        OrderId orderId = OrderId.of("123");
        LineItem lineItem1 = new LineItem(ProductId.of("uuid-1"), Quantity.of(new BigDecimal("10"), Unit.EACH), Amount.of(new BigDecimal("10"), Currency.EUR));
        Order mockOrder = Order.create(orderId, new LineItems(List.of(lineItem1)),
                Amount.of(BigDecimal.TEN, Currency.GBP));

        when(orderOutputPort.getOrderById(orderId)).thenReturn(mockOrder);

        Order result = orderService.getOrderById(orderId);

        assertThat(result)
                .isNotNull()
                .isEqualTo(mockOrder);

        verify(orderOutputPort).getOrderById(orderId);
        verifyNoMoreInteractions(orderOutputPort);
    }

    @Test
    void shouldCreateOrderAndPublishEvent() {
        // Arrange
        List<LineItem> items = List.of(
                new LineItem(
                        ProductId.of("P-1"),
                        Quantity.of(new BigDecimal("2"), Unit.EACH),
                        Amount.of(new BigDecimal("50.00"), Currency.GBP)
                )
        );
        OrderId generatedId = OrderId.of("456");

        when(idGenerator.generateId()).thenReturn("456");
        when(pricingService.calculateTotalPrice(eq(items), eq(Currency.GBP), any(BigDecimal.class)))
                .thenReturn(Amount.of(new BigDecimal("100.00"), Currency.GBP));

        Order savedOrder = Order.create(
                generatedId,
                new LineItems(items),
                Amount.of(new BigDecimal("100.00"), Currency.GBP)
        );
        when(orderOutputPort.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order result = orderService.createOrder(items);

        // Assert
        assertThat(result)
                .isNotNull()
                .extracting(Order::getOrderId)
                .isEqualTo(generatedId);

        // verify pricing invocation shape
        verify(pricingService).calculateTotalPrice(eq(items), eq(Currency.GBP), any(BigDecimal.class));

        // verify persistence and event
        verify(orderOutputPort).save(any(Order.class));

        ArgumentCaptor<OrderCreatedEvent> createdEvent = ArgumentCaptor.forClass(OrderCreatedEvent.class);
        verify(domainEventPublisher).publish(createdEvent.capture());
        assertThat(createdEvent.getValue().orderId()).isEqualTo(generatedId);

        verifyNoMoreInteractions(orderOutputPort, domainEventPublisher, pricingService);
    }

    @Test
    void shouldCancelOrderAndPublishEvent() {
        OrderId orderId = OrderId.of("789");
        LineItem lineItem1 = new LineItem(ProductId.of("uuid-1"), Quantity.of(new BigDecimal("10"), Unit.EACH), Amount.of(new BigDecimal("10"), Currency.EUR));
        Order order = Order.create(orderId, new LineItems(List.of(lineItem1)),
                Amount.of(BigDecimal.ONE, Currency.GBP));

        when(orderOutputPort.getOrderById(orderId)).thenReturn(order);
        when(orderOutputPort.update(order)).thenReturn(order);

        Order result = orderService.cancelOrder(orderId);

        assertThat(result.getStatus()).isEqualTo(OrderStatus.CANCELLED);

        ArgumentCaptor<OrderCancelledEvent> cancelledEvent = ArgumentCaptor.forClass(OrderCancelledEvent.class);
        verify(domainEventPublisher).publish(cancelledEvent.capture());
        assertThat(cancelledEvent.getValue().orderId()).isEqualTo(orderId);

        verify(orderOutputPort).getOrderById(orderId);
        verify(orderOutputPort).update(order);
        verifyNoMoreInteractions(orderOutputPort, domainEventPublisher);
    }

    @Test
    void shouldThrowExceptionIfCancelOrderIdIsNull() {
        assertThatThrownBy(() -> orderService.cancelOrder(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("OrderId is null");

        verifyNoInteractions(orderOutputPort, domainEventPublisher, pricingService);
    }
}
