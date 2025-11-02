package org.manualway.simple;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private String runMainAndCapture(String... args) {
        PrintStream orig = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            Main.main(args);
        } finally {
            System.setOut(orig);
        }
        return baos.toString();
    }

    @Test
    void main_withStripe_defaultOrExplicit_printsStripeFlow() {
        // default (no args) -> stripe
        String outDefault = runMainAndCapture();
        assertTrue(outDefault.contains("Using gateway: stripe"));
        assertTrue(outDefault.contains("[Stripe] Charging 49.99"));

        // explicit stripe
        String outStripe = runMainAndCapture("stripe");
        assertTrue(outStripe.contains("Using gateway: stripe"));
        assertTrue(outStripe.contains("[Stripe] Charging 49.99"));
    }

    @Test
    void main_withPaypal_printsPaypalFlow() {
        String out = runMainAndCapture("paypal");
        assertTrue(out.contains("Using gateway: paypal"));
        assertTrue(out.contains("[PayPal] Charging 49.99"));
    }

    @Test
    void main_withUnknown_throws() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Main.main(new String[]{"unknown"})
        );
        assertTrue(ex.getMessage().contains("Unsupported payment gateway type"));
    }
}
