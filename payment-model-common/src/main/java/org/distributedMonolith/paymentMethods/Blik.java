package org.distributedMonolith.paymentMethods;

public class Blik extends PaymentMethod {
    private String blikCode;

    public Blik(String blikCode) {
        this.blikCode = blikCode;
    }

    public String getBlikCode() {
        return blikCode;
    }

    public void setBlikCode(String blikCode) {
        this.blikCode = blikCode;
    }

    @Override
    public String toString() {
        return "Blik{" +
                "blikCode='" + blikCode + '\'' +
                '}';
    }
}
