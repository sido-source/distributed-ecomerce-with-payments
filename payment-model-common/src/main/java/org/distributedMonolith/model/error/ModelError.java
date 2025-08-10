package org.distributedMonolith.model.error;

import org.distributedMonolith.event.DomainErrorCode;

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

class ServiceLayer {

    class ServiceLayer {

        private final RoutingPolicy routing;
        private final Map<PaymentProviderType, ProviderClient> clients;
        private final List<ProviderErrorMapper> errorMappers;

        public InstructionResponse<AuthorizationResult> authorize(
                PaymentInstruction instruction,
                PaymentMethod method,
                CustomerRef customer,
                String returnUrl
        ) {
            PaymentProviderType provider = routing.choose(
                    instruction.region(),
                    method.type(),
                    instruction.amount(),
                    instruction.amount().currency(),
                    clients.keySet()
            );

            instruction.routeTo(provider);
            PaymentAttempt attempt = instruction.startAttempt(provider);

            InstructionRequest<AuthorizeCommand> req = new InstructionRequest<>(
                    instruction.id(),
                    new AuthorizeCommand(
                            instruction.amount(),
                            instruction.captureMode(),
                            method.token(),
                            customer,
                            returnUrl,
                            Map.of("orderId", instruction.orderId())
                    ),
                    Instant.now()
            );

            InstructionResponse<AuthorizationResult> res;
            try {
                res = clients.get(provider).authorize(req);
            } catch (Exception e) {
                // Truly exceptional path (network meltdown, bad config, serialization, etc.)
                ModelError err = new ServiceError(DomainErrorCode.NETWORK_ERROR, e.getMessage());
                PaymentErrorRecord rec = ErrorFactory.toRecord(
                        instruction.id(),
                        PaymentStep.AUTHORIZATION_FAILED,
                        err,
                        provider.name(),
                        null,
                        Instant.now(),
                        null
                );
                attempt.markFailed(err.code().name(), err.message());
                instruction.markError(err.code().name(), err.message());
                // Return a failure response (don’t force callers to catch)
                return new InstructionResponse<>(instruction.id(), false, null, err, Instant.now());
            }

            if (res.success()) {
                // happy path
                attempt.markAuthorized(res.payload().providerRef());
                instruction.markAuthorized();
                return res; // already success=true
            }

            // failure path from provider (no throw)
            // If adapter already provided a ModelError in res.error(), use it; otherwise map from raw
            ModelError err = res.error();
            if (err == null) {
                ProviderErrorMapper mapper = errorMappers.stream()
                        .filter(m -> m.supports(provider))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No error mapper for " + provider));
                err = mapper.mapAuthorizationError(extractRaw(res)); // implement extractRaw(..) in your adapter layer
            }

            // persist/log record
            PaymentErrorRecord rec = ErrorFactory.toRecord(
                    instruction.id(),
                    PaymentStep.AUTHORIZATION_FAILED,
                    err,
                    provider.name(),
                    extractPspRef(res),         // implement to pull PSP ref from res/payload if present
                    Instant.now(),
                    extractRawJson(res)         // implement to pull/sanitize raw response text
            );

            attempt.markFailed(err.code().name(), err.message());
            instruction.markError(err.code().name(), err.message());

            // Return a normalized failure response
            return new InstructionResponse<>(instruction.id(), false, null, err, Instant.now());
        }

        // placeholders you’ll implement in adapters layer
        private Object extractRaw(InstructionResponse<?> res) { return null; }
        private String extractRawJson(InstructionResponse<?> res) { return null; }
        private String extractPspRef(InstructionResponse<?> res) { return null; }
    }

}
