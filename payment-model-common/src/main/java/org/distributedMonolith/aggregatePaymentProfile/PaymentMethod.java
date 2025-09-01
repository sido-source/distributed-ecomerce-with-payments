package org.distributedMonolith.aggregatePaymentProfile;

import java.time.Instant;

public interface PaymentMethod {
    public PaymentRef paymentRef();
    public CustomerRef customer(PaymentRef paymentRef);
    PaymentMethodType getType();
    List<ProviderToken> tokens(PaymentRef paymentRef);
    BillingAddress billingAddress(PaymentRef paymentRef);
    boolean isDefault();
    Instant createdAt();
}