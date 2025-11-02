package org.factory.pattern.domain;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PaypalGatewayTest {

    @Test
    void name_isPaypal() {
        assertEquals("paypal", new PaypalGateway().name());
    }

    @Test
    void charge_prints() {
        PaypalGateway gw = new PaypalGateway();
        PrintStream orig = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            gw.charge(12.34);
        } finally {
            System.setOut(orig);
        }
        assertTrue(baos.toString().contains("[PayPal] Charging 12.34"));
    }
}
