package org.distributedMonolith;

import com.adyen.model.payment.CancelRequest;
import com.adyen.model.payment.PaymentRequest;

public class IAdyenRequest {
    Request getRequest();
    PspType getPspType();
    Request createRequest();

}

class AdyenAuthorizationRequest extends PaymentRequest {
    // This class can be extended to include specific fields for Adyen authorization requests
    public AdyenAuthorizationRequest() {
        // Default constructor
        super();
    }
}

class AdyenCaptureRequest extends PaymentRequest {
    // This class can be extended to include specific fields for Adyen capture requests
    public AdyenCaptureRequest() {
        // Default constructor
        super();
    }
}

class AdyenRefundRequest extends PaymentRequest {
    // This class can be extended to include specific fields for Adyen refund requests
    public AdyenRefundRequest() {
        // Default constructor
        super();
    }
}

class AdyenCancelAuthorizationRequest extends CancelRequest {
    // This class can be extended to include specific fields for Adyen cancel authorization requests
    public AdyenCancelAuthorizationRequest() {
        // Default constructor
        super();
    }
}