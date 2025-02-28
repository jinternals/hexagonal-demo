package com.jinternals.demo.domain.model;


import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class LineItems {
    private final List<LineItem> items;

    public LineItems(List<LineItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one line item");
        }
        this.items = List.copyOf(items); // Immutable copy
    }

    public List<LineItem> items() {
        return Collections.unmodifiableList(items);
    }

    public int totalItems() {
        return items.size();
    }
}

