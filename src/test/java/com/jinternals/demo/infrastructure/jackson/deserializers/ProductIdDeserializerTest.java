package com.jinternals.demo.infrastructure.jackson.deserializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jinternals.demo.domain.model.OrderId;
import com.jinternals.demo.domain.model.ProductId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIdDeserializerTest {

    private final ObjectMapper objectMapper;

    public ProductIdDeserializerTest() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ProductId.class, new ProductIdDeserializer());

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(module);
    }

    @Test
    void shouldDeserializeValidProductId() throws JsonProcessingException {
        String json = "\"550e8400-e29b-41d4-a716-446655440000\"";
        ProductId productId = objectMapper.readValue(json, ProductId.class);

        assertThat(productId).isNotNull();
        assertThat(productId.value()).isEqualTo("550e8400-e29b-41d4-a716-446655440000");
    }

    @Test
    void shouldHandleNullDeserialization() throws JsonProcessingException {
        String json = "null";

        OrderId orderId = objectMapper.readValue(json, OrderId.class);

        assertThat(orderId).isNull();
    }
}