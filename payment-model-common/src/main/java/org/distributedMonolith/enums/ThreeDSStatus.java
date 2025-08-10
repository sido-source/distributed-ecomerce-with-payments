package org.distributedMonolith.enums;

public enum ThreeDSStatus {
    /**
     * The 3D Secure status is not applicable, meaning the transaction does not require 3D Secure authentication.
     */

    /**
     * The 3D Secure challenge is required, meaning the transaction requires additional authentication.
     */

    /**
     * The 3D Secure challenge was successfully completed, meaning the authentication was successful.
     */

    /**
     * The 3D Secure challenge failed, meaning the authentication was not successful.
     */


    NOT_APPLICABLE,
    CHALLENGE_REQUIRED,
    CHALLENGE_SUCCEEDED,
    CHALLENGE_FAILED
}
