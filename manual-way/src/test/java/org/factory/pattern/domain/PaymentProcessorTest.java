package org.factory.pattern.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PaymentProcessorTest {
    @Test
    void testProcessWithPaypal() {
        PaymentGateway gateway = new PaypalGateway();
        PaymentProcessor processor = new PaymentProcessor(gateway);
        assertDoesNotThrow(() -> processor.process(50.0));
    }

    @Test
    void testProcessWithStripe() {
        PaymentGateway gateway = new StripeGateway();
        PaymentProcessor processor = new PaymentProcessor(gateway);
        assertDoesNotThrow(() -> processor.process(75.0));
    }
}

