package org.factory.pattern.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StripeGatewayTest {
    @Test
    void testName() {
        StripeGateway gateway = new StripeGateway();
        assertEquals("stripe", gateway.name());
    }

    @Test
    void testCharge() {
        StripeGateway gateway = new StripeGateway();
        // No exception should be thrown
        assertDoesNotThrow(() -> gateway.charge(200.0));
    }
}

