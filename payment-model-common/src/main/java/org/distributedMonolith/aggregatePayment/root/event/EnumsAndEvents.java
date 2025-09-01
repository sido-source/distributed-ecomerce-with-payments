package org.distributedMonolith.aggregatePayment.root.event;

import java.time.ZonedDateTime;


public interface PaymentEvent {
    String getPaymentId();
    ZonedDateTime getEventDate();
}

public class PaymentAuthorizedEvent implements PaymentEvent {
    private final String paymentId;
    private final String pspRef;
    private final ZonedDateTime date;

    public PaymentAuthorizedEvent(String paymentId, String pspRef, ZonedDateTime date) {
        this.paymentId = paymentId;
        this.pspRef = pspRef;
        this.date = date;
    }

    public String getPaymentId() { return paymentId; }
    public ZonedDateTime getEventDate() { return date; }
}

public class PaymentCapturedEvent implements PaymentEvent { /* similar */ }
public class PaymentRefundedEvent implements PaymentEvent { /* similar */ }
public class PaymentFailedEvent implements PaymentEvent { /* similar */ }