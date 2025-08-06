package org.distributedMonolith.model;

import org.distributedMonolith.enums.PaymentAction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

// request - response pair them together to one Method/Action like Atuhorize, Capture, Refund, Cancel
// each request either response can have an error list as one request can be send many times because of some failures

public class PaymentAggregate123 {
    private final String id;
    private final String orderId;
    private final BigDecimal amount;
    private final Currency currency;
    private final PSPType pspType;
    private PaymentStatus status;
    private PaymentStep step; // sequence step immutable, which all are in some order
    private final InfrastructureMetadata metadata;
    private final List<String> errors = new ArrayList<>();
    // we have to keep track of requests and responses for each action to be now on what step we are right now
    private final Map<PaymentAction,List<PaymentCommonRestApi>> actionsAndRequestWithResponses = new HashMap<>(); // how to maybe add some abstraction that would make it more clear
    private final List<PaymentEvent> domainEvents = new ArrayList<>();

    public PaymentAggregate123(String id, String orderId, BigDecimal amount, Currency currency, PSPType pspType) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.pspType = pspType;
        this.status = PaymentStatus.PENDING;
        this.step = PaymentStep.INITIALIZED;
        this.metadata = new InfrastructureMetadata();
    }


    public void markAuthorized(String pspRef) {
        this.status = PaymentStatus.AUTHORIZED;
        this.step = PaymentStep.AUTH_COMPLETED;
        this.metadata.setPspReference(pspRef);
        addEvent(new PaymentAuthorizedEvent(id, pspRef, ZonedDateTime.now()));
    }

    public void markCaptured(String pspRef) {
        this.status = PaymentStatus.CAPTURED;
        this.step = PaymentStep.CAPTURE_COMPLETED;
        this.metadata.setPspReference(pspRef);
        addEvent(new PaymentCapturedEvent(id, pspRef, ZonedDateTime.now()));
    }

    public void markRefunded(String pspRef) {
        this.status = PaymentStatus.REFUNDED;
        this.step = PaymentStep.REFUND_COMPLETED;
        this.metadata.setPspReference(pspRef);
        addEvent(new PaymentRefundedEvent(id, pspRef, ZonedDateTime.now()));
    }

    public void markFailed(String error) {
        this.status = PaymentStatus.FAILED;
        this.step = PaymentStep.ERROR;
        this.metadata.addError(error);
        addEvent(new PaymentFailedEvent(id, error, ZonedDateTime.now()));
    }

    public void incrementRetry() {
        metadata.incrementRetry();
    }

    private void addEvent(PaymentEvent e) {
        domainEvents.add(e);
    }
}

class UserAggregateForPayment {
    private final String id;
    private final String orderId;
    private final BigDecimal amount;
    private final Currency currency;
    private List<Integer> orderIds = new ArrayList<>();
    private List<PaymentAggregate> payments = new ArrayList<>();

    public OrderAggregateForPayment(String id, String orderId, BigDecimal amount, Currency currency, ZonedDateTime timestamp) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
    }
}

// abstraction for request and response, each can have multiple errors
// like request can't be sent because of some error, then it is retried
// response can have errors like payment was declined, or some other error
class PaymentCommonRestApi {
    private final String id;
    private final PaymentAction action;
    private final ZonedDateTime timestamp;
    private final String pspReference;
    private final List<PaymentError> errors;
    private final ApiCommunicationType apiCommunicationType;


    public PaymentCommonRestApi(String id, PaymentAction action, ZonedDateTime timestamp, String pspReference, ApiCommunicationType apiCommunicationType) {
        this.id = id;
        this.action = action;
        this.timestamp = timestamp;
        this.pspReference = pspReference;
        this.apiCommunicationType = apiCommunicationType;
        this.errors = new ArrayList<>();
    }

    public void addError(String error) {
        errors.add(error);
    }
}

enum ApiCommunicationType {
    REQUEST,
    RESPONSE
}

enum PaymentError {
    ApiCommunicationType apiCommunicationType;
    String errorCode;
    String errorMessage;
    String pspReference;
    ZonedDateTime timestamp;
    PaymentStep step;
    int retryCount;

    PaymentError(ApiCommunicationType apiCommunicationType, String errorCode, String errorMessage, String pspReference, ZonedDateTime timestamp) {
        this.apiCommunicationType = apiCommunicationType;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.pspReference = pspReference;
        this.timestamp = timestamp;
    }
}

enum PaymentStatus {
    PENDING,
    AUTHORIZED,
    CAPTURED,
    REFUNDED,
    FAILED
}

enum PaymentStep {
    INITIALIZED,
    AUTHORIZATION_COMPLETED, // we got the auth response with sucess or failure
    AUTHORIZATION_FAILED,
    CAPTURE_COMPLETED,
    CAPTURE_FAILED,
    REFUND_COMPLETED,
    REFUND_FAILED,
    CANCEL_AUTHORIZATION_COMPLETED,
    CANCEL_AUTHORIZATION_FAILED,
}