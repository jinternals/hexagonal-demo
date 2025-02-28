package com.jinternals.demo.infrastructure.jackson.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jinternals.demo.domain.model.OrderId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderIdSerializerTest {

    private final ObjectMapper objectMapper;

    public OrderIdSerializerTest() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(OrderId.class, new OrderIdSerializer());

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(module);
    }

    @Test
    void shouldSerializeValidOrderId() throws JsonProcessingException {

        String orderId = objectMapper.writeValueAsString(new OrderId("some-order-id"));

        assertThat(orderId).isNotBlank();
        assertThat(orderId).isEqualTo("\"some-order-id\"");

    }


}