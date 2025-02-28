package com.jinternals.demo.domain.services;

import com.jinternals.demo.domain.model.Amount;
import com.jinternals.demo.domain.model.Currency;
import com.jinternals.demo.domain.model.LineItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderPricingService {
    public Amount calculateTotalPrice(List<LineItem> items, Currency currency, BigDecimal exchangeRate) {
        BigDecimal total = items.stream()
                .map(item -> item.getPrice().convertTo(currency, exchangeRate).value().multiply(item.getQuantity().value()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Amount(total, currency);
    }
}
