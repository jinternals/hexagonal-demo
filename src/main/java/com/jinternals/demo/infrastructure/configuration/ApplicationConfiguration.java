package com.jinternals.demo.infrastructure.configuration;

import com.jinternals.demo.domain.services.IdGenerator;
import com.jinternals.demo.infrastructure.services.UUIDIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public IdGenerator idGenerator() {
        return new UUIDIdGenerator();
    }
}
