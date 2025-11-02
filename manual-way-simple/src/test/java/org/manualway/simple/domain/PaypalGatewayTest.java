package org.manualway.simple.domain;

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
    void charge_printsAmount() {
        PaypalGateway gw = new PaypalGateway();

        PrintStream orig = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            gw.charge(12.34);
        } finally {
            System.setOut(orig);
        }

        String out = baos.toString();
        assertTrue(out.contains("[PayPal] Charging 12.34"));
    }
}
