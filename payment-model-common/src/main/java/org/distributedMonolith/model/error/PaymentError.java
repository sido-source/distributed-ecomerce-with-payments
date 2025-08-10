package org.distributedMonolith.model.error;

import java.time.ZonedDateTime;

public class PaymentError extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    public PaymentError(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

class PaymentError {
    String paymentId;
    String errorCode;
    String errorMessage;
    String pspReference;
    ZonedDateTime timestamp;
    PaymentStep step;
    String rawResponse; // raw response from the PSP

    PaymentError(String paymentId, String errorCode, String errorMessage, String pspReference, ZonedDateTime timestamp) {
        this.paymentId = paymentId;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.pspReference = pspReference;
        this.timestamp = timestamp;
    }
}