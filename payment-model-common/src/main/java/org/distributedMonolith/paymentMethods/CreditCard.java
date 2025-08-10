package org.distributedMonolith.paymentMethods;

public class CreditCard extends PaymentMethod {
    private String card4Number;
    private String cardHolderName;
    private String expiryDate;

    public CreditCard(String card4Number, String cardHolderName, String expiryDate) {
        this.card4Number = card4Number;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
    }

    public String getCard4Number() {
        return card4Number;
    }

    public void setCard4Number(String cardNumber) {
        this.card4Number = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
