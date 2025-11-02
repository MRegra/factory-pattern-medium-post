package org.example.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
class StripeGatewayTest {

    @Test
    void name_isStripe() {
        assertEquals("stripe", new StripeGateway().name());
    }

    @Test
    void charge_prints(CapturedOutput output) {
        new StripeGateway().charge(10.0);
        assertTrue(output.getOut().contains("[Stripe] Charging 10.0"));
    }
}
