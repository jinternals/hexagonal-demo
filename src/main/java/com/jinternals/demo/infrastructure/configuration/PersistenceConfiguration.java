package com.jinternals.demo.infrastructure.configuration;

import com.jinternals.demo.infrastructure.adapter.out.persistence.OrderOutputPersistenceAdapter;
import com.jinternals.demo.infrastructure.adapter.out.persistence.repositories.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

    @Bean
    OrderOutputPersistenceAdapter orderPersistenceAdapter(OrderRepository orderRepository) {
        return new OrderOutputPersistenceAdapter(orderRepository);
    }

}
