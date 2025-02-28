package com.jinternals.demo.domain.events;

import java.time.Instant;

public interface DomainEvent {
    Instant timestamp();
    String getEventType();
}
