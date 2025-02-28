package com.jinternals.demo.infrastructure.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jinternals.demo.domain.model.OrderId;

import java.io.IOException;

public class OrderIdDeserializer extends StdDeserializer<OrderId> {
    public OrderIdDeserializer() {
        super(OrderId.class);
    }

    @Override
    public OrderId deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return new OrderId(parser.getText());
    }
}
