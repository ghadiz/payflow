package com.ghadyz.payflow.domain;

import java.time.Instant;
import java.util.UUID;

public record PaymentEvent(
        UUID eventId,
        String fromService,
        String toService,
        double amountGbp,
        PaymentStatus status,
        Instant timestamp,
        long latencyMs
) {
    public static PaymentEvent create(
            String fromService,
            String toService,
            double amountGbp,
            PaymentStatus status,
            long latencyMs
    ) {
        return new PaymentEvent(
                UUID.randomUUID(),
                fromService,
                toService,
                amountGbp,
                status,
                Instant.now(),
                latencyMs
        );
    }
}