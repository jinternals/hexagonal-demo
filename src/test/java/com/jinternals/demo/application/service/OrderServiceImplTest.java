package com.jinternals.demo.application.service;

import com.jinternals.demo.application.port.out.DomainEventPublisher;
import com.jinternals.demo.application.port.out.OrderOutputPort;
import com.jinternals.demo.domain.logger.DomainLogger;
import com.jinternals.demo.domain.model.*;
import com.jinternals.demo.domain.services.IdGenerator;
import com.jinternals.demo.domain.services.OrderPricingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderPricingService pricingService;
    @Mock
    private OrderOutputPort orderOutputPort;
    @Mock
    private DomainEventPublisher domainEventPublisher;
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private DomainLogger log;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void name() {

        List<LineItem> items = List.of(
                new LineItem(ProductId.of("uuid-1"), new Quantity(new BigDecimal(10), Unit.EACH), new Amount(new BigDecimal(10), Currency.EUR)),
                new LineItem(ProductId.of("uuid-2"), new Quantity(new BigDecimal(10), Unit.EACH), new Amount(new BigDecimal(10), Currency.EUR))
        );
        Order order = orderService.createOrder(items);

    }
}