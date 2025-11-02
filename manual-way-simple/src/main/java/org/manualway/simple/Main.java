package org.manualway.simple;

import org.manualway.simple.domain.PaymentGateway;
import org.manualway.simple.domain.PaymentProcessor;
import org.manualway.simple.domain.PaypalGateway;
import org.manualway.simple.domain.StripeGateway;

public class Main {

    public static void main(String[] args) {
        String type = (args.length > 0 ? args[0] : "stripe").toLowerCase();

        PaymentGateway gateway;
        if (type.equals("paypal")) {
            gateway = new PaypalGateway();
        } else if (type.equals("stripe")) {
            gateway = new StripeGateway();
        } else {
            throw new IllegalArgumentException("Unsupported payment gateway type: " + type);
        }
        PaymentProcessor processor = new PaymentProcessor(gateway);
        processor.process(49.99);
    }
}