package org.distributedMonolith.aggregatePayment.children.authorization;

public interface IAuthorization {
    authorize();
    authorizeWith3ds();
    limitForWaitingExceeded();
    supportPsp();
}
