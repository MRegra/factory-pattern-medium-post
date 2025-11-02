package org.factory.pattern;

import org.factory.pattern.dependencyinjection.Config;
import org.factory.pattern.dependencyinjection.MyApplicationContext;
import org.factory.pattern.domain.PaymentGateway;
import org.factory.pattern.domain.PaymentProcessor;
import org.factory.pattern.factory.PaymentGatewayFactory;

public class Main {
    public static void main(String[] args) {
        Config config = new Config("app.properties");
        MyApplicationContext ctx = new MyApplicationContext(config);

        // Beans
        ctx.registerBean(PaymentProcessor.class);
        ctx.registerBean(PaymentGateway.class, () -> new PaymentGatewayFactory(config).create());

        PaymentProcessor processor = ctx.getBean(PaymentProcessor.class);
        processor.process(49.99);
    }
}