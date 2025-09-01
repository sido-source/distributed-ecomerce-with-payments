package org.distributedMonolith.aggregatePayment.root.event;

public enum DomainErrorCode {
    AUTHENTICATION_REQUIRED,
    CARD_DECLINED,
    INSUFFICIENT_FUNDS,
    FRAUD_SUSPECTED,
    INVALID_REQUEST,
    TEMPORARY_PROVIDER_ERROR,
    UNKNOWN
}
