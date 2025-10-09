package org.factory.pattern;

import org.factory.pattern.dependencyinjection.Config;
import org.factory.pattern.dependencyinjection.MyApplicationContext;
import org.factory.pattern.domain.PaymentGateway;
import org.factory.pattern.domain.PaymentProcessor;

public class Main {
    public static void main(String[] args) {
        Config config = new Config("app.properties");
        MyApplicationContext ctx = new MyApplicationContext(config);

        // Register root beans (container can infer dependencies recursively)
        ctx.registerBean(PaymentProcessor.class);
        ctx.registerBean(PaymentGateway.class); // virtual; resolved by factory hook

        PaymentProcessor processor = ctx.getBean(PaymentProcessor.class);
        processor.process(49.99);
    }
}