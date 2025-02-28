package com.jinternals.demo.infrastructure.adapter.in.rest;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedLineItem {
    private String productId;
    private BigDecimal quantity;
    private String quantityUnit;
    private BigDecimal price;
    private String priceCurrency;
}

