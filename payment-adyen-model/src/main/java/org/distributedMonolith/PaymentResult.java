package com.example.payment.adyen.transform;

public class PaymentResult<T> {
    private final T response;
    private final boolean success;
    private final String refusalReason;

    public PaymentResult(T response, boolean success, String refusalReason) {
        this.response = response;
        this.success = success;
        this.refusalReason = refusalReason;
    }

    public T getResponse() { return response; }
    public boolean isSuccess() { return success; }
    public String getRefusalReason() { return refusalReason; }
}