package org.distributedMonolith.model;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserAggregateForPayment {
    private final String userId;
    private final List<String> orderIds = new ArrayList<>();
    private final List<PaymentAggregate> payments = new ArrayList<>();
    private final ZonedDateTime createdAt;
    private ZonedDateTime lastUpdated;

    public UserAggregateForPayment(String userId) {
        this.userId = userId;
        this.createdAt = ZonedDateTime.now();
        this.lastUpdated = this.createdAt;
    }

    public void addPayment(PaymentAggregate payment) {
        payments.add(payment);
        if (!orderIds.contains(payment.getOrderId())) {
            orderIds.add(payment.getOrderId());
        }
        this.lastUpdated = ZonedDateTime.now();
    }

    public List<PaymentAggregate> getPaymentsByOrderId(String orderId) {
        return payments.stream()
                .filter(p -> p.getOrderId().equals(orderId))
                .collect(Collectors.toList());
    }

    public Map<PaymentStatus, Long> getPaymentStatusCounts() {
        return payments.stream()
                .collect(Collectors.groupingBy(PaymentAggregate::getStatus, Collectors.counting()));
    }

    public long countFailedAttempts() {
        return payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.FAILED)
                .count();
    }

    public boolean hasSuspiciousActivity() {
        // example: more than 3 failed attempts across orders in short time
        return countFailedAttempts() > 3;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public List<String> getOrderIds() {
        return Collections.unmodifiableList(orderIds);
    }

    public List<PaymentAggregate> getPayments() {
        return Collections.unmodifiableList(payments);
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }
}

