package com.jinternals.demo.infrastructure.configuration;

import com.jinternals.demo.application.port.out.DomainEventPublisher;
import com.jinternals.demo.infrastructure.adapter.out.event.KafkaDomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class EventConfiguration {

    @Bean
    public DomainEventPublisher domainEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaDomainEventPublisher(kafkaTemplate);
    }
}
