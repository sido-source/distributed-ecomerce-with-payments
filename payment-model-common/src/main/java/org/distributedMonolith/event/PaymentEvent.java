package org.distributedMonolith.event;

import org.distributedMonolith.enums.EventType;
import org.distributedMonolith.enums.PspType;

public abstract class PaymentEvent {
    private final String id;
    private final String pspReference;
    private final boolean success;
    private final EventType eventType;
    private final PspType pspType;

    protected PaymentEvent(String id, String pspReference, boolean success, EventType eventType, PspType pspType) {
        this.id = id;
        this.pspReference = pspReference;
        this.success = success;
        this.eventType = eventType;
        this.pspType = pspType;
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
