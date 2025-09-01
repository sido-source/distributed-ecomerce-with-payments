package org.distributedMonolith.aggregatePayment.root.event;

import org.distributedMonolith.aggregatePayment.root.DomainEventType;
import org.distributedMonolith.aggregatePayment.root.enums.PspType;

public abstract class PaymentEvent {
    private final String id;
    private final String pspReference;
    private final boolean success;
    private final DomainEventType domainEventType;
    private final PspType pspType;

    protected PaymentEvent(String id, String pspReference, boolean success, DomainEventType domainEventType, PspType pspType) {
        this.id = id;
        this.pspReference = pspReference;
        this.success = success;
        this.domainEventType = domainEventType;
        this.pspType = pspType;
    }

    public String getId() {
        return id;
    }

    public String getPspReference() {
        return pspReference;
    }

    public boolean isSuccess() {
        return success;
    }
}
