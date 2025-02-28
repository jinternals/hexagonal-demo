package com.jinternals.demo.infrastructure.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jinternals.demo.domain.model.OrderId;

import java.io.IOException;

public class OrderIdSerializer extends StdSerializer<OrderId> {
    public OrderIdSerializer() {
        super(OrderId.class);
    }

    @Override
    public void serialize(OrderId orderId, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(orderId.value().toString());
    }
}
