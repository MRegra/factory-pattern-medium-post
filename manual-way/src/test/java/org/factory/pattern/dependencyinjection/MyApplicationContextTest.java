package org.factory.pattern.dependencyinjection;

import org.factory.pattern.domain.PaymentProcessor;
import org.factory.pattern.factory.PaymentGatewayFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyApplicationContextTest {
    @Test
    void testRegisterAndGetBean() {
        Config config = new Config("test-app.properties");
        MyApplicationContext ctx = new MyApplicationContext(config);
        ctx.registerBean(PaymentGatewayFactory.class);
        ctx.registerBean(PaymentProcessor.class);
        PaymentProcessor processor = ctx.getBean(PaymentProcessor.class);
        assertNotNull(processor);
    }

    @Test
    void testMissingBeanDefinitionThrows() {
        Config config = new Config("test-app.properties");
        MyApplicationContext ctx = new MyApplicationContext(config);
        assertThrows(IllegalStateException.class, () -> ctx.getBean(PaymentProcessor.class));
    }
}
