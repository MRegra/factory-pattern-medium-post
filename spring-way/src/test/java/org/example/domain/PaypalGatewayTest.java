package org.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class PaypalGatewayTest {

    @Test
    void name_isPaypal() {
        assertEquals("paypal", new PaypalGateway().name());
    }

    @Test
    void charge_prints(CapturedOutput output) {
        new PaypalGateway().charge(10.0);
        assertTrue(output.getOut().contains("[PayPal] Charging 10.0"));
    }
}
