package com.ghadyz.payflow.infrastructure.kafka;


import com.ghadyz.payflow.domain.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    @KafkaListener(
            topics = "payment-events",
            groupId = "payflow-consumer-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            @Payload PaymentEvent event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
            ) {
        log.info("Received event {} | partition = {} offset = {} | {} -> {} | {} | {}ms",
                event.eventId(),
                partition,
                offset,
                event.fromService(),
                event.toService(),
                event.status(),
                event.latencyMs());

        processEvent(event);
    }

    private void processEvent(PaymentEvent event){
        log.debug("Processing event: {}", event.eventId());
    }
}
