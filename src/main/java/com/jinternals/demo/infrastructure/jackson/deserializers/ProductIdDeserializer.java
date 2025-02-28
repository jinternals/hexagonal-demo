package com.jinternals.demo.infrastructure.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jinternals.demo.domain.model.OrderId;
import com.jinternals.demo.domain.model.ProductId;

import java.io.IOException;
import java.util.UUID;

public class ProductIdDeserializer extends StdDeserializer<ProductId> {
    public ProductIdDeserializer() {
        super(ProductId.class);
    }

    @Override
    public ProductId deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return new ProductId(parser.getText());
    }
}
