package com.ghadyz.payflow.infrastructure.kafka;

import com.ghadyz.payflow.domain.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private static final String TOPIC = "payment-events";

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void send(PaymentEvent event){
        kafkaTemplate.send(TOPIC, event.eventId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send event {} to Kafka: {}",
                                event.eventId(), ex.getMessage());
                    } else {
                        log.info("sent event {} | {} -> {} | {} | {}ms",
                                event.eventId(),
                                event.fromService(),
                                event.toService(),
                                event.status(),
                                event.latencyMs());
                    }
                });
    }
}
