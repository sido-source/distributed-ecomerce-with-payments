package org.distributedMonolith.enums;

/**
 * Enum representing the steps in the payment process.
 * Each step corresponds to a specific action taken during the payment lifecycle.
 * This enum is used to track low-level details of the progress.
 */
public enum PaymentStep {
    INITIALIZED,
    AUTHORIZATION_COMPLETED, AUTHORIZATION_FAILED,
    CAPTURE_COMPLETED, CAPTURE_FAILED,
    REFUND_COMPLETED, REFUND_FAILED,
    CANCEL_AUTHORIZATION_COMPLETED, CANCEL_AUTHORIZATION_FAILED,
    ERROR
}
