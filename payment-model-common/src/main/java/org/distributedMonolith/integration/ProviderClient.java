package org.distributedMonolith.integration;

public interface ProviderClient {
    PaymentProviderType provider();
    InstructionResponse<AuthorizationResult> authorize(InstructionRequest<AuthorizeCommand> cmd);
    InstructionResponse<CaptureResult> capture(InstructionRequest<CaptureCommand> cmd);
    InstructionResponse<VoidResult> cancel(InstructionRequest<CancelCommand> cmd);
    InstructionResponse<RefundResult> refund(InstructionRequest<RefundCommand> cmd);
}

/ --- COMMANDS/RESULTS ---
public record AuthorizeCommand(Money amount, CaptureMode captureMode, ProviderToken token, CustomerRef customer, String returnUrl, Map<String, String> metadata) {}
public record AuthorizationResult(ProviderRef providerRef, ThreeDSStatus threeDSStatus) {}
public record CaptureCommand(ProviderRef providerAuthRef, Money amount) {}
public record CaptureResult(ProviderRef providerCaptureRef) {}
public record CancelCommand(ProviderRef providerAuthRef) {}
public record VoidResult() {}
public record RefundCommand(ProviderRef providerCaptureRef, Money amount, String reason) {}
public record RefundResult(ProviderRef providerRefundRef) {}