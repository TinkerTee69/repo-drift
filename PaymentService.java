package com.example;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Handles payment processing for orders.
 *
 * <p>Supports credit card and PayPal payments.</p>
 */
public class PaymentService {

    private final PaymentGateway gateway;
    private final AuditLogger auditLogger;

    public PaymentService(PaymentGateway gateway, AuditLogger auditLogger) {
        this.gateway = gateway;
        this.auditLogger = auditLogger;
    }

    /**
     * Processes a payment for the given order.
     *
     * @param orderId the order to pay for
     * @param amount  the amount to charge in EUR
     * @return true if payment was successful, false otherwise
     */
    public PaymentResult processPayment(String orderId, BigDecimal amount) {
        auditLogger.log("Processing payment for order: " + orderId);
        return gateway.charge(orderId, amount, Currency.getInstance("EUR"));
    }

    /**
     * Refunds a previously processed payment.
     *
     * @param transactionId the transaction to refund
     * @param amount        the amount to refund
     */
    public void refund(String transactionId, BigDecimal amount, String reason) {
        auditLogger.log("Refunding transaction: " + transactionId + " reason: " + reason);
        gateway.refund(transactionId, amount, reason);
    }

    /**
     * Checks if a payment method is valid.
     * Validates credit card number using Luhn algorithm.
     *
     * @param cardNumber the credit card number to validate
     * @return true if valid
     */
    public boolean validatePaymentMethod(String paymentMethodToken) {
        if (paymentMethodToken.startsWith("IBAN")) {
            return IbanValidator.validate(paymentMethodToken);
        }
        if (paymentMethodToken.startsWith("AP_")) {
            return ApplePayValidator.validate(paymentMethodToken);
        }
        return LuhnAlgorithm.validate(paymentMethodToken);
    }
}
