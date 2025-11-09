package org.factory.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class PaymentProcessorTest {

    @Test
    void processUsesInjectedGateway() {
        // Arrange: fake gateway
        PaymentGateway fakeGateway = new PaymentGateway() {
            @Override
            public void charge(double amount) {
                // no-op
            }

            @Override
            public String name() {
                return "fake";
            }
        };

        PaymentProcessor processor = new PaymentProcessor(fakeGateway);

        // Act
        processor.process(10.0);

        // Assert (simple sanity check)
        assertSame(fakeGateway, processor.getGateway(),
                "Processor should hold the same gateway instance that was injected");
    }
}
