package org.example.config;

import org.example.domain.PaymentProcessor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class PaymentConfigTest {

    @Nested
    @SpringBootTest(classes = PaymentConfig.class)
    class DefaultStripe {
        @org.springframework.beans.factory.annotation.Autowired
        PaymentProcessor processor;

        @Test
        void defaultsToStripe(CapturedOutput output) {
            processor.process(49.99);
            String out = output.getOut();
            assertTrue(out.contains("Using gateway: stripe"));
            assertTrue(out.contains("[Stripe] Charging 49.99"));
        }
    }

    @Nested
    @SpringBootTest(classes = PaymentConfig.class, properties = "payment.gateway=StRiPe")
    class CaseInsensitiveStripe {
        @org.springframework.beans.factory.annotation.Autowired
        PaymentProcessor processor;

        @Test
        void stripeCaseInsensitive(CapturedOutput output) {
            processor.process(49.99);
            String out = output.getOut();
            assertTrue(out.contains("Using gateway: stripe"));
            assertTrue(out.contains("[Stripe] Charging 49.99"));
        }
    }

    @Nested
    @SpringBootTest(classes = PaymentConfig.class, properties = "payment.gateway=paypal")
    class Paypal {
        @org.springframework.beans.factory.annotation.Autowired
        PaymentProcessor processor;

        @Test
        void selectsPaypal(CapturedOutput output) {
            processor.process(49.99);
            String out = output.getOut();
            assertTrue(out.contains("Using gateway: paypal"));
            assertTrue(out.contains("[PayPal] Charging 49.99"));
        }
    }

    @Test
    void unknownGateway_failsContext() {
        new ApplicationContextRunner()
                .withUserConfiguration(PaymentConfig.class)
                .withPropertyValues("payment.gateway=bogus")
                .run(ctx -> assertThrows(Throwable.class, () -> ctx.getBean(PaymentProcessor.class)));
    }
}
