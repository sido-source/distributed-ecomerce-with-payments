package org.distributedMonolith.aggregatePayment.children.authorization;

public interface IAuthRouter {
    void routePaymentToRightProcessor();
}
