package org.example.config;

import org.example.domain.PaymentGateway;
import org.example.domain.PaymentProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PaymentConfig.class)
@TestPropertySource(properties = "payment.gateway=StRiPe")
class PaymentConfigCaseInsensitiveTest {
    @Autowired
    private PaymentGateway paymentGateway;
    @Autowired
    private PaymentProcessor paymentProcessor;

    @Test
    void testCaseInsensitiveGatewayBean() {
        assertNotNull(paymentGateway);
        assertEquals("StripeGateway", paymentGateway.getClass().getSimpleName());
    }

    @Test
    void testPaymentProcessorBean() {
        assertNotNull(paymentProcessor);
        assertEquals(paymentGateway, paymentProcessor.getGateway());
    }
}

