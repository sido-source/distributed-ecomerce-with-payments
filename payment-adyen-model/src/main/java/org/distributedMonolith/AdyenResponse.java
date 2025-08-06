package org.distributedMonolith;

import com.adyen.service.payment.PaymentsApi;

public class AdyenResponse {

    PaymentsApi adyenResponse;

    public AdyenResponse() {
        // Default constructor
        adyenResponse = new PaymentsApi();
        adyenResponse.authorise()
    }

    class AdyenAuthroizationResponse {

    }

    class AdyenCaptureResponse {
    }

    class AdyenRefundResponse {
    }

    class AdyenCancelAuthorizationResponse {
    }
}
