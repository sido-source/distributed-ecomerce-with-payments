package org.distributedMonolith.paymentMethods;

import java.time.Instant;

public abstract class PaymentMethod {
    private final String id; // internal UUID
    private final CustomerRef customer;
    private final PaymentMethodType type;
    private final List<ProviderToken> tokens; // opaque, safe to store
    private final BillingAddress billingAddress; // optional
    private final boolean isDefault;
    private final Instant createdAt;

    private PaymentMethod(Builder b) {
        this.id = b.id;
        this.customer = b.customer;
        this.type = b.type;
        this.token = b.token;
        this.billingAddress = b.billingAddress;
        this.isDefault = b.isDefault;
        this.createdAt = b.createdAt == null ? Instant.now() : b.createdAt;
        if (id == null || customer == null || type == null || token == null) {
            throw new IllegalStateException("PaymentMethod missing mandatory fields");
        }
    }
    public String id() { return id; }
    public CustomerRef customer() { return customer; }
    public PaymentMethodType type() { return type; }
    public ProviderToken token() { return token; }
    public BillingAddress billingAddress() { return billingAddress; }
    public boolean isDefault() { return isDefault; }
    public Instant createdAt() { return createdAt; }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private CustomerRef customer;
        private PaymentMethodType type;
        private ProviderToken token;
        private BillingAddress billingAddress;
        private boolean isDefault;
        private Instant createdAt;
        public Builder customer(CustomerRef c) { this.customer = c; return this; }
        public Builder type(PaymentMethodType t) { this.type = t; return this; }
        public Builder token(ProviderToken t) { this.token = t; return this; }
        public Builder billingAddress(BillingAddress a) { this.billingAddress = a; return this; }
        public Builder setDefault(boolean d) { this.isDefault = d; return this; }
        public Builder createdAt(Instant i) { this.createdAt = i; return this; }
        public PaymentMethod build() { return new PaymentMethod(this); }
    }
}