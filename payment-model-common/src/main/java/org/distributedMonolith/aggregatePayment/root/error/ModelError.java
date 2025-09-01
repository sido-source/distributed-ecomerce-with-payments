package org.distributedMonolith.aggregatePayment.root.error;

import org.distributedMonolith.aggregatePayment.root.event.DomainErrorCode;

public sealed interface ModelError permits BusinessError, ServiceError {
    DomainErrorCode code();
    String message();
    ErrorCategory category();
}

/** Business-level error (e.g., card declined, 3DS failed). */
public record BusinessError(DomainErrorCode code, String message) implements ModelError {
    @Override public ErrorCategory category() { return ErrorCategory.BUSINESS; }
}

/** Service/technical error (e.g., network issue, bad signature, config). */
public record ServiceError(DomainErrorCode code, String message) implements ModelError {
    @Override public ErrorCategory category() { return ErrorCategory.SERVICE; }
}