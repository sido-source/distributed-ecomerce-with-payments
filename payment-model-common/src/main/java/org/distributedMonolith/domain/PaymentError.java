package org.distributedMonolith.domain;

import org.distributedMonolith.aggregatePayment.root.error.ErrorCategory;

public class PaymentError {
    long id;

    ErrorCategory errorCategory;
    String description;
}
