package org.distributedMonolith.model;

import org.distributedMonolith.enums.PaymentAction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

// request - response pair them together to one Method/Action like Atuhorize, Capture, Refund, Cancel
// each request either response can have an error list as one request can be send many times because of some failures

// abstraction for request and response, each can have multiple errors
// like request can't be sent because of some error, then it is retried
// response can have errors like payment was declined, or some other error
class PaymentCommonRestApi {
    private final String id;
    private final PaymentAction action;
    private final ZonedDateTime timestamp;
    private final String pspReference;
    private final List<PaymentError> errors;
    private final ApiCommunicationType apiCommunicationType;


    public PaymentCommonRestApi(String id, PaymentAction action, ZonedDateTime timestamp, String pspReference, ApiCommunicationType apiCommunicationType) {
        this.id = id;
        this.action = action;
        this.timestamp = timestamp;
        this.pspReference = pspReference;
        this.apiCommunicationType = apiCommunicationType;
        this.errors = new ArrayList<>();
    }

    public void addError(String error) {
        errors.add(error);
    }
}

// --- PORTS ---
public interface ProviderClient {
    PaymentProviderType provider();
    InstructionResponse<AuthorizationResult> authorize(InstructionRequest<AuthorizeCommand> cmd);
    InstructionResponse<CaptureResult> capture(InstructionRequest<CaptureCommand> cmd);
    InstructionResponse<VoidResult> cancel(InstructionRequest<CancelCommand> cmd);
    InstructionResponse<RefundResult> refund(InstructionRequest<RefundCommand> cmd);
}

// --- COMMANDS/RESULTS ---
public record AuthorizeCommand(Money amount, CaptureMode captureMode, ProviderToken token, CustomerRef customer, String returnUrl, Map<String, String> metadata) {}
public record AuthorizationResult(ProviderRef providerRef, ThreeDSStatus threeDSStatus) {}
public record CaptureCommand(ProviderRef providerAuthRef, Money amount) {}
public record CaptureResult(ProviderRef providerCaptureRef) {}
public record CancelCommand(ProviderRef providerAuthRef) {}
public record VoidResult() {}
public record RefundCommand(ProviderRef providerCaptureRef, Money amount, String reason) {}
public record RefundResult(ProviderRef providerRefundRef) {}

enum ApiCommunicationType {
    REQUEST,
    RESPONSE
}

