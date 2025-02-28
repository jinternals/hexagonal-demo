package com.jinternals.demo.infrastructure.configuration;

import com.jinternals.demo.application.port.out.DomainEventPublisher;
import com.jinternals.demo.application.port.out.OrderOutputPort;
import com.jinternals.demo.application.service.OrderService;
import com.jinternals.demo.application.service.OrderServiceImpl;
import com.jinternals.demo.domain.services.IdGenerator;
import com.jinternals.demo.domain.services.OrderPricingService;
import com.jinternals.demo.infrastructure.logger.Slf4jDomainLogger;
import com.jinternals.demo.infrastructure.services.TransactionalOrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OrderServiceConfiguration {

    @Bean
    public OrderPricingService orderPricingService() {
        return new OrderPricingService();
    }

    @Primary
    @Bean("transactionalOrderService")
    public OrderService transactionalOrderService(OrderService orderService) {
        return new TransactionalOrderServiceImpl(orderService);
    }

    @Bean("orderService")
    public OrderService orderService(
            OrderPricingService orderPricingService,
            OrderOutputPort orderOutputPort,
            DomainEventPublisher domainEventPublisher,
            IdGenerator idGenerator) {
        return new OrderServiceImpl(orderPricingService, orderOutputPort, domainEventPublisher, idGenerator, new Slf4jDomainLogger(OrderServiceImpl.class));
    }


}
