package org.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
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
    void process_printsAndDelegatesCharge(CapturedOutput output) {
        StubGateway gw = new StubGateway();
        PaymentProcessor processor = new PaymentProcessor(gw);

        processor.process(49.99);

        assertEquals(49.99, gw.lastAmount, 1e-9);
        String out = output.getOut();
        assertTrue(out.contains("Using gateway: stub"));
    }
}
