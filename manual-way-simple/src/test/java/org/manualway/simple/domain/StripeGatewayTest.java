package org.manualway.simple.domain;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StripeGatewayTest {

    @Test
    void name_isStripe() {
        assertEquals("stripe", new StripeGateway().name());
    }

    @Test
    void charge_printsAmount() {
        StripeGateway gw = new StripeGateway();

        PrintStream orig = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            gw.charge(56.78);
        } finally {
            System.setOut(orig);
        }

        String out = baos.toString();
        assertTrue(out.contains("[Stripe] Charging 56.78"));
    }
}
