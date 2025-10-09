package org.factory.pattern.factory;

import org.factory.pattern.dependencyinjection.Config;
import org.factory.pattern.domain.PaymentGateway;
import org.factory.pattern.domain.PaypalGateway;
import org.factory.pattern.domain.StripeGateway;

public class PaymentGatewayFactory {
    private final Config config;

    public PaymentGatewayFactory(Config config) {
        this.config = config;
    }

    public PaymentGateway create() {
        String type = config.get("payment.gateway");
        if (type == null) throw new IllegalArgumentException("Missing 'payment.gateway' property");
        return switch (type.toLowerCase()) {
            case "stripe" -> new StripeGateway();
            case "paypal" -> new PaypalGateway();
            default -> throw new IllegalArgumentException("Unknown gateway: " + type);
        };
    }
}
