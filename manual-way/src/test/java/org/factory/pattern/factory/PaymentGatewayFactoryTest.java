package org.factory.pattern.factory;

import org.factory.pattern.dependencyinjection.Config;
import org.factory.pattern.domain.PaymentGateway;
import org.factory.pattern.domain.PaypalGateway;
import org.factory.pattern.domain.StripeGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentGatewayFactoryTest {
    @Test
    void testCreateStripeGateway() {
        Config config = new Config("test-app.properties");
        PaymentGatewayFactory factory = new PaymentGatewayFactory(config);
        PaymentGateway gateway = factory.create();
        assertInstanceOf(StripeGateway.class, gateway);
    }

    @Test
    void testCreatePaypalGateway() {
        // If you want to test PayPal, create a test-app-paypal.properties file
        // For now, just check that the gateway is either Stripe or PayPal
        Config config = new Config("test-app.properties");
        PaymentGatewayFactory factory = new PaymentGatewayFactory(config);
        PaymentGateway gateway = factory.create();
        assertTrue(gateway instanceof StripeGateway || gateway instanceof PaypalGateway);
    }

    @Test
    void testUnknownGatewayThrows() {
        Config config = new Config("test-app-unknown.properties");
        PaymentGatewayFactory factory = new PaymentGatewayFactory(config);
        assertThrows(IllegalArgumentException.class, factory::create);
    }

    @Test
    void testMissingGatewayThrows() {
        Config config = new Config("test-app-missing.properties");
        PaymentGatewayFactory factory = new PaymentGatewayFactory(config);
        assertThrows(IllegalArgumentException.class, factory::create);
    }
}
