package org.factory.config;

import org.junit.jupiter.api.Test;
import org.factory.domain.PaymentGateway;
import org.factory.domain.PaymentProcessor;
import org.factory.domain.PaypalGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = "payment.gateway=PAYPAL")
class PaymentConfigPaypalTest {

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private PaymentProcessor paymentProcessor;

    @Test
    void paymentGatewayIsPaypalWhenConfigured() {
        assertTrue(paymentGateway instanceof PaypalGateway,
                "Expected PaymentGateway bean to be PaypalGateway when payment.gateway=PAYPAL");
    }

    @Test
    void paymentProcessorUsesSameGatewayBean() {
        assertEquals(paymentGateway, paymentProcessor.getGateway(),
                "PaymentProcessor should use the same PaymentGateway bean from the context");
    }
}
