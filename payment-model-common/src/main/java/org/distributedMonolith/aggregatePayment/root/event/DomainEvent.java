package org.distributedMonolith.aggregatePayment.root.event;

public class DomainEvent {
    private final String id;
    private final String pspReference;
    private final DomainErrorCode eventType;
}
