package org.factory.config;

import org.factory.domain.PaymentGateway;
import org.factory.domain.PaymentProcessor;
import org.factory.domain.StripeGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
@TestPropertySource(properties = "payment.gateway=STRIPE")
class PaymentConfigStripeTest {

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private PaymentProcessor paymentProcessor;

    @Test
    void paymentGatewayIsStripeWhenConfigured() {
        assertInstanceOf(StripeGateway.class, paymentGateway, "Expected PaymentGateway bean to be StripeGateway when payment.gateway=STRIPE");
    }

    @Test
    void paymentProcessorUsesSameGatewayBean() {
        assertEquals(paymentGateway, paymentProcessor.getGateway(),
                "PaymentProcessor should use the same PaymentGateway bean from the context");
    }
}
