package com.jinternals.demo.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    @Bean
    public DefaultKafkaProducerFactoryCustomizer producerCustomizer(ObjectMapper objectMapper) {
        return (producerFactory) -> {

            producerFactory.updateConfigs(Map.of(
                    "key.serializer", StringSerializer.class,
                    "value.serializer", JsonSerializer.class // ✅ FIX: Pass class reference instead of an instance
            ));

            // Inject custom ObjectMapper into the existing JsonSerializer
            producerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
        };
    }

}
