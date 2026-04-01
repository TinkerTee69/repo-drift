# payment-service-demo

Payment processing service for the shop platform.

## Supported payment methods

- Credit card (Visa, Mastercard)
- PayPal

**Note: This README is outdated. PayPal was replaced by SEPA direct debit
and Apple Pay in version 2.0. The API has also changed significantly.**

## API

### Process a payment

```
POST /payments
{
  "orderId": "ORD-123",
  "amount": 49.99,
  "method": "credit_card",
  "cardNumber": "4111111111111111"
}
```

Returns `true` if successful.

**Note: Response changed in v2 - now returns a PaymentResult object
with transactionId, status and timestamp. The boolean response
described above no longer exists.**

### Refund

```
POST /payments/{transactionId}/refund
{
  "amount": 49.99
}
```

**Note: A mandatory `reason` field was added in v2 but is not
documented here.**

### Validate payment method

```
POST /payments/validate
{
  "cardNumber": "4111111111111111"
}
```

Validates credit card numbers using the Luhn algorithm.

**Note: Now also validates SEPA IBANs and Apple Pay tokens,
but this endpoint documentation was never updated.**

## Setup

```bash
./mvnw spring-boot:run
```
