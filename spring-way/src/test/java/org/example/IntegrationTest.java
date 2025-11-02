package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class IntegrationTest {

    @Test
    void runsWithPaypal(CapturedOutput output) {
        Main.main(new String[] {"--payment.gateway=paypal"});
        String out = output.getOut();
        assertTrue(out.contains("Using gateway: paypal"));
        assertTrue(out.contains("[PayPal] Charging 49.99"));
    }
}
