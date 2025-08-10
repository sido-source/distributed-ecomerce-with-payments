package org.distributedMonolith.enums;

public enum CaptureMode {
    AUTOMATIC("Automatic", "Payment is captured automatically after authorization."),
    MANUAL("Manual", "Payment requires manual capture/settlement after authorization.");

    private final String mode;
    private final String description;

    CaptureMode(String mode, String description) {
        this.mode = mode;
        this.description = description;
    }

    public String getMode() {
        return mode;
    }

    public String getDescription() {
        return description;
    }
}