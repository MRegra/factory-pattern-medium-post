package org.example.domain;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class PaymentGatewayTest {

    static Stream<PaymentGateway> gateways() {
        return Stream.of(new StripeGateway(), new PaypalGateway());
    }

    @ParameterizedTest
    @MethodSource("gateways")
    void name_isLowercaseKeyword(PaymentGateway gw) {
        assertTrue(
                gw.name().equals("stripe") || gw.name().equals("paypal"),
                "name() should return 'stripe' or 'paypal'"
        );
    }

    @ParameterizedTest
    @MethodSource("gateways")
    void charge_printsPrefixAndAmount(PaymentGateway gw, CapturedOutput output) {
        gw.charge(12.34);
        String out = output.getOut();
        if (gw instanceof StripeGateway) {
            assertTrue(out.contains("[Stripe] Charging 12.34"));
        } else {
            assertTrue(out.contains("[PayPal] Charging 12.34"));
        }
    }
}
