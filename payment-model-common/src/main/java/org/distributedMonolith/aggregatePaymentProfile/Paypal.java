package org.distributedMonolith.aggregatePaymentProfile;

public class Paypal extends PaymentMethod {
    private String email;
    private String nonce;

    public Paypal(String email, String password) {
        this.email = email;
        this.nonce = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Paypal{" +
                "email='" + email + '\'' +
                ", password='" + nonce + '\'' +
                '}';
    }
}
