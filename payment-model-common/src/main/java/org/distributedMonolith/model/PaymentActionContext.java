package org.distributedMonolith.model;

import org.distributedMonolith.model.envelope.PaymentRequestEnvelope;
import org.distributedMonolith.model.envelope.PaymentResponseEnvelope;
import org.distributedMonolith.enums.PaymentAction;

import java.util.ArrayList;
import java.util.List;

public class PaymentActionContext<TRequest, TResponse> {

    private final PaymentAction action;
    private final String orderId;
    private final String userId;
    private final List<PaymentRequestEnvelope<TRequest>> requestAttempts = new ArrayList<>();
    private final List<PaymentResponseEnvelope<TResponse>> responses = new ArrayList<>();
    private final List<PaymentError> errors = new ArrayList<>();

    public PaymentActionContext(PaymentAction action, String orderId, String userId) {
        this.action = action;
        this.orderId = orderId;
        this.userId = userId;
    }

    public void addRequest(PaymentRequestEnvelope<TRequest> request) {
        requestAttempts.add(request);
    }

    public void addResponse(PaymentResponseEnvelope<TResponse> response) {
        responses.add(response);
        if (!response.isSuccess()) {
            this.errors.addAll(response.getErrors());
        }
    }

    public void addError(PaymentError error) {
        this.errors.add(error);
    }

    public Optional<PaymentResponseEnvelope<TResponse>> latestResponse() {
        return responses.isEmpty()
                ? Optional.empty()
                : Optional.of(responses.get(responses.size() - 1));
    }

    public List<PaymentError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    // Other getters ...
}
