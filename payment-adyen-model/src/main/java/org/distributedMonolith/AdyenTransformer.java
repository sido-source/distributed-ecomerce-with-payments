package com.example.payment.adyen.transform;

import com.adyen.model.checkout.Amount;
import com.adyen.model.checkout.PaymentRequest;
import com.adyen.model.checkout.PaymentResponse;
import com.adyen.model.checkout.PaymentResponse.ResultCodeEnum;
import com.example.payment.common.model.*;

import java.util.Map;

public class AdyenTransformer {

    public static PaymentRequest toAdyenRequest(CommonPayment cp) {
        PaymentRequest req = new PaymentRequest();
        req.setMerchantAccount(cp.getMetadata().getMerchantReference());
        req.setReference(cp.getId());

        Amount amount = new Amount();
        amount.setCurrency(cp.getCurrency().getCurrencyCode());
        amount.setValue(cp.getAmount().multiply(new java.math.BigDecimal("100")).longValue());
        req.setAmount(amount);

        req.setMetadata(Map.of("traceId", cp.getMetadata().getTraceId()));
        return req;
    }

    public static PaymentResult<PaymentResponse> handleResponse(PaymentResponse response, CommonPayment cp) {
        boolean success = response.getResultCode() == ResultCodeEnum.AUTHORISED;
        if (success) {
            cp.markAuthorized(response.getPspReference());
        } else {
            cp.markFailed(response.getRefusalReason());
        }
        return new PaymentResult<>(response, success, response.getRefusalReason());
    }
}