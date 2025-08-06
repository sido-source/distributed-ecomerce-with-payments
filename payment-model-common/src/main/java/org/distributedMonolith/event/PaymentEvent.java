package org.distributedMonolith.event;

public abstract class PaymentEvent {
    private final String id;
    private final String pspReference;
    private final boolean success;
    private final EventType eventType;

    protected PaymentEvent(String id, String pspReference, boolean success) {
        this.id = id;
        this.pspReference = pspReference;
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public String getPspReference() {
        return pspReference;
    }

    public boolean isSuccess() {
        return success;
    }
}
