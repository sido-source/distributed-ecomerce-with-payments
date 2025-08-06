package org.distributedMonolith.model.envelope;

import org.distributedMonolith.enums.PaymentAction;

import java.time.ZonedDateTime;
import java.util.*;

public class PaymentRequestEnvelope<T> {
    private final String id = UUID.randomUUID().toString();
    private final T request; // Could be AdyenRequest, Getnet, etc.
    private final PaymentAction action;
    private final ZonedDateTime timestamp = ZonedDateTime.now();
    private final String traceId;
    private final String merchantReference;
    private final Map<String, String> headers = new HashMap<>();
    private final List<String> validationErrors = new ArrayList<>();
    private int retryCount = 0;

    public PaymentRequestEnvelope(T request, PaymentAction action, String traceId, String merchantReference) {
        this.request = request;
        this.action = action;
        this.traceId = traceId;
        this.merchantReference = merchantReference;
    }

    public void addValidationError(String error) {
        validationErrors.add(error);
    }

    public void incrementRetry() {
        retryCount++;
    }

    // Getters omitted for brevity
}

