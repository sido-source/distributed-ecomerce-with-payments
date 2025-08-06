package org.distributedMonolith.model;

package com.example.payment.common.model;

import org.distributedMonolith.model.envelope.PaymentRequestEnvelope;
import org.distributedMonolith.model.envelope.PaymentResponseEnvelope;
import org.distributedMonolith.enums.PaymentAction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

// Aggregate Root
public class PaymentAggregate {
    private final String id;
    private final String orderId;
    private final BigDecimal amount;
    private final Currency currency;
    private final PSPType pspType;
    private PaymentStatus status;
    private PaymentStep step;
    private final InfrastructureMetadata metadata = new InfrastructureMetadata();

    private final List<PaymentEvent> domainEvents = new ArrayList<>();
    private final Map<PaymentAction, PaymentActionContext<?, ?>> actionContexts = new HashMap<>();

    public <TReq, TResp> void recordRequest(PaymentAction action,
                                            PaymentRequestEnvelope<TReq> requestEnvelope) {
        PaymentActionContext<TReq, TResp> context =
                (PaymentActionContext<TReq, TResp>) actionContexts.computeIfAbsent(action,
                        a -> new PaymentActionContext<>(a, requestEnvelope.getOrderId(), requestEnvelope.getUserId()));
        context.addRequest(requestEnvelope);
    }

    public <TReq, TResp> void recordResponse(PaymentAction action,
                                             PaymentResponseEnvelope<TResp> responseEnvelope) {
        PaymentActionContext<TReq, TResp> context =
                (PaymentActionContext<TReq, TResp>) actionContexts.get(action);
        if (context != null) {
            context.addResponse(responseEnvelope);
        }
    }

    public void recordError(PaymentAction action, PaymentError error) {
        PaymentActionContext<?, ?> context = actionContexts.get(action);
        if (context != null) {
            ((PaymentActionContext<?, ?>) context).addError(error);
        }
    }

    public Optional<PaymentActionContext<?, ?>> getContextFor(PaymentAction action) {
        return Optional.ofNullable(actionContexts.get(action));
    }

    public List<PaymentError> getAllErrors() {
        return actionContexts.values().stream()
                .flatMap(ctx -> ctx.getErrors().stream())
                .toList();
    }

    public PaymentAggregate(String id, String orderId, BigDecimal amount, Currency currency, PSPType pspType) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.pspType = pspType;
        this.status = PaymentStatus.PENDING;
        this.step = PaymentStep.INITIALIZED;
    }

    public void addAttempt(PaymentAction action, PaymentAttempt attempt) {
        attempts.computeIfAbsent(action, k -> new ArrayList<>()).add(attempt);
        if (attempt.hasErrors()) {
            this.metadata.incrementRetry();
        }
    }

    public void markAuthorized(String pspRef) {
        this.status = PaymentStatus.AUTHORIZED;
        this.step = PaymentStep.AUTHORIZATION_COMPLETED;
        this.metadata.setPspReference(pspRef);
        addEvent(new PaymentAuthorizedEvent(id, pspRef, ZonedDateTime.now()));
    }

    public void markCaptured(String pspRef) {
        this.status = PaymentStatus.CAPTURED;
        this.step = PaymentStep.CAPTURE_COMPLETED;
        this.metadata.setPspReference(pspRef);
        addEvent(new PaymentCapturedEvent(id, pspRef, ZonedDateTime.now()));
    }

    public void markRefunded(String pspRef) {
        this.status = PaymentStatus.REFUNDED;
        this.step = PaymentStep.REFUND_COMPLETED;
        this.metadata.setPspReference(pspRef);
        addEvent(new PaymentRefundedEvent(id, pspRef, ZonedDateTime.now()));
    }

    public void markFailed(String error) {
        this.status = PaymentStatus.FAILED;
        this.step = PaymentStep.ERROR;
        this.metadata.addError(error);
        addEvent(new PaymentFailedEvent(id, error, ZonedDateTime.now()));
    }

    private void addEvent(PaymentEvent e) {
        domainEvents.add(e);
    }
}

