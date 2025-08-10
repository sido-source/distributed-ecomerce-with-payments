package org.distributedMonolith.integration;

import java.util.function.Function;

public class PaymentMethodTransformers {

    Function<AdyenPaymentMethod, PaymentMethod> adyenToModel = adyenPaymentMethod -> {
        if (adyenPaymentMethod == null) {
            return null;
        }
        return new PaymentMethod(adyenPaymentMethod.getType(), adyenPaymentMethod.getBrand(),
                adyenPaymentMethod.getFundingSource(), adyenPaymentMethod.getStoredPaymentMethodId());
    };
}
