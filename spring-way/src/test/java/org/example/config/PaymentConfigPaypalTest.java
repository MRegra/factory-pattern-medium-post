package org.example.config;

import org.example.domain.PaymentGateway;
import org.example.domain.PaymentProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PaymentConfig.class)
@TestPropertySource(properties = "payment.gateway=paypal")
class PaymentConfigPaypalTest {
    @Autowired
    private PaymentGateway paymentGateway;
    @Autowired
    private PaymentProcessor paymentProcessor;

    @Test
    void testPaypalGatewayBean() {
        assertNotNull(paymentGateway, "PaymentGateway bean should not be null");
        assertEquals("PaypalGateway", paymentGateway.getClass().getSimpleName(), "Should be PaypalGateway");
    }

    @Test
    void testPaymentProcessorBean() {
        assertNotNull(paymentProcessor, "PaymentProcessor bean should not be null");
        assertEquals(paymentGateway, paymentProcessor.getGateway(), "Processor should use the injected gateway");
    }
}
