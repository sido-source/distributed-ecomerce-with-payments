package org.distributedMonolith.integration;

public interface ProviderErrorMapper {
    boolean supports(PaymentProviderType provider);
    ModelError mapAuthorizationError(Object rawProviderError);
    ModelError mapCaptureError(Object rawProviderError);
    ModelError mapRefundError(Object rawProviderError);
    ModelError mapCancelError(Object rawProviderError);
}