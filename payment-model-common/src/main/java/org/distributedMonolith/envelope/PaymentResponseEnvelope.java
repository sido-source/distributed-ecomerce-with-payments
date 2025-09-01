package org.distributedMonolith.envelope;

import org.distributedMonolith.aggregatePayment.root.enums.PaymentAction;

import java.time.ZonedDateTime;
import java.util.*;

public class PaymentResponseEnvelope<T> {
    private final String id = UUID.randomUUID().toString();
    private final T response;
    private final ZonedDateTime receivedAt = ZonedDateTime.now();
    private final String pspReference;
    private final boolean success;
    private final List<String> errors = new ArrayList<>();
    private final PaymentAction action;

    public PaymentResponseEnvelope(T response, String pspReference, boolean success, PaymentAction action) {
        this.response = response;
        this.pspReference = pspReference;
        this.success = success;
        this.action = action;
    }

    public void addError(String error) {
        errors.add(error);
    }

    // Getters omitted for brevity
}

