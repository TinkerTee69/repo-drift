# payment-service-demo

Payment processing service for the shop platform

## Supported payment methods

- Credit card (Visa, Mastercard)
- PayPal

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


### Refund

```
POST /payments/{transactionId}/refund
{
  "amount": 49.99
}
```


### Validate payment method

```
POST /payments/validate
{
  "cardNumber": "4111111111111111"
}
```

Validates credit card numbers using the Luhn algorithm.


## Setup

```bash
./mvnw spring-boot:run
```
