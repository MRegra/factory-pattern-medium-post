package org.manualway.simple.domain;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentProcessorTest {

    static class StubGateway implements PaymentGateway {
        double lastAmount = Double.NaN;

        @Override
        public void charge(double amount) {
            lastAmount = amount;
        }

        @Override
        public String name() {
            return "stub";
        }
    }

    @Test
    void process_callsChargeOnGateway_andPrintsGatewayName() {
        StubGateway gw = new StubGateway();
        PaymentProcessor p = new PaymentProcessor(gw);

        // capture System.out
        PrintStream orig = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            p.process(49.99);
        } finally {
            System.setOut(orig);
        }

        assertEquals(49.99, gw.lastAmount, 1e-9, "Gateway should receive the amount");
        String out = baos.toString();
        assertTrue(out.contains("Using gateway: stub"), "Should print the gateway name");
    }
}
