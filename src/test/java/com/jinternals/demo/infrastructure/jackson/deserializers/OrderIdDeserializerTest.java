package com.jinternals.demo.infrastructure.jackson.deserializers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jinternals.demo.domain.model.OrderId;
import org.junit.jupiter.api.Test;


class OrderIdDeserializerTest {

    private final ObjectMapper objectMapper;

    public OrderIdDeserializerTest() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OrderId.class, new OrderIdDeserializer());

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(module);
    }

    @Test
    void shouldDeserializeValidOrderId() throws JsonProcessingException {
        String json = "\"550e8400-e29b-41d4-a716-446655440000\"";
        OrderId orderId = objectMapper.readValue(json, OrderId.class);

        assertThat(orderId).isNotNull();
        assertThat(orderId.value()).isEqualTo("550e8400-e29b-41d4-a716-446655440000");
    }

    @Test
    void shouldHandleNullDeserialization() throws JsonProcessingException {
        String json = "null";

        OrderId orderId = objectMapper.readValue(json, OrderId.class);

        assertThat(orderId).isNull();
    }
}
