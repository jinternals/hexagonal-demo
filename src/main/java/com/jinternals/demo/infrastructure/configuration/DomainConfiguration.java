package com.jinternals.demo.infrastructure.configuration;

import com.jinternals.demo.application.port.out.DomainEventPublisher;
import com.jinternals.demo.application.port.out.OrderOutputPort;
import com.jinternals.demo.application.service.OrderService;
import com.jinternals.demo.application.service.OrderServiceImpl;
import com.jinternals.demo.domain.services.IdGenerator;
import com.jinternals.demo.domain.services.OrderPricingService;
import com.jinternals.demo.infrastructure.logger.Slf4jDomainLogger;
import com.jinternals.demo.infrastructure.services.TransactionalOrderServiceImpl;
import com.jinternals.demo.infrastructure.services.UUIDIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DomainConfiguration {

    @Bean
    public IdGenerator idGenerator() {
        return new UUIDIdGenerator();
    }


}
