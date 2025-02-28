package com.jinternals.demo.infrastructure.configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jinternals.demo.domain.model.OrderId;
import com.jinternals.demo.domain.model.ProductId;
import com.jinternals.demo.infrastructure.jackson.deserializers.OrderIdDeserializer;
import com.jinternals.demo.infrastructure.jackson.deserializers.ProductIdDeserializer;
import com.jinternals.demo.infrastructure.jackson.serializers.OrderIdSerializer;
import com.jinternals.demo.infrastructure.jackson.serializers.ProductIdSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfiguration {

        @Bean
        @Primary
        public SimpleModule valueObjectModule() {
            SimpleModule module = new SimpleModule();

            module.addSerializer(OrderId.class, new OrderIdSerializer());
            module.addDeserializer(OrderId.class, new OrderIdDeserializer());

            module.addSerializer(ProductId.class, new ProductIdSerializer());
            module.addDeserializer(ProductId.class, new ProductIdDeserializer());

            return module;
        }
}