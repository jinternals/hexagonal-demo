package com.jinternals.demo.infrastructure.jackson.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jinternals.demo.domain.model.ProductId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIdSerializerTest {

    private final ObjectMapper objectMapper;

    public ProductIdSerializerTest() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(ProductId.class, new ProductIdSerializer());

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(module);
    }

    @Test
    void shouldSerializeValidProductId() throws JsonProcessingException {

        String orderId = objectMapper.writeValueAsString(new ProductId("some-product-id"));

        assertThat(orderId).isNotBlank();
        assertThat(orderId).isEqualTo("\"some-product-id\"");

    }
}