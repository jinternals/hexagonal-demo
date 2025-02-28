package com.jinternals.demo.infrastructure.messaging;

import com.jinternals.demo.domain.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class KafkaOrderEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaOrderEventPublisher.class);
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    private static final String TOPIC = "orders.created";

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publish(OrderCreatedEvent event) {
        log.info("Publishing order event: {}", event);
        kafkaTemplate.send(TOPIC, event.orderId().value().toString(), event);
    }

}