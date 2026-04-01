package com.example;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Handles payment processing for orders.
 *
 * <p>Supports credit card and PayPal payments.</p>
 *
 * NOTE: This comment is outdated - PayPal support was removed in v2.
 * Now supports credit card, SEPA direct debit and Apple Pay.
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
     *
     * NOTE: Return type changed - now returns PaymentResult with
     * transaction ID and status, not a boolean. Comment is wrong.
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
     *
     * NOTE: Parameter 'reason' was added but not documented.
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
     *
     * NOTE: Method was extended - now also validates SEPA IBANs
     * and Apple Pay tokens, but Javadoc only mentions credit cards.
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
