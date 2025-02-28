package com.jinternals.demo.infrastructure.adapter.in.rest;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @Size(min=1, max=5)
    private List<OrderedLineItem> lineItems;

}
