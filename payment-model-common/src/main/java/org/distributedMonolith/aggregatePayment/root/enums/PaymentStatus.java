package org.distributedMonolith.aggregatePayment.root.enums;

/**
 * Enum representing the status of a payment, high level status.
 */
public enum PaymentStatus {
    PENDING,
    AUTHORIZED,
    AUTHORIZE_CANCELED,
    CAPTURED,
    REFUNDED,
    FAILED
}
