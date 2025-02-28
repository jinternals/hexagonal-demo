package com.jinternals.demo.infrastructure.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jinternals.demo.domain.model.OrderId;
import com.jinternals.demo.domain.model.ProductId;

import java.io.IOException;

public class ProductIdSerializer extends StdSerializer<ProductId> {
    public ProductIdSerializer() {
        super(ProductId.class);
    }

    @Override
    public void serialize(ProductId orderId, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(orderId.value());
    }
}
