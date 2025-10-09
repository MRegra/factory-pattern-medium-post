package org.factory.pattern.domain;

public class PaymentProcessor {
    private final PaymentGateway gateway;

    public PaymentProcessor(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void process(double amount) {
        System.out.println("Using gateway: " + gateway.name());
        gateway.charge(amount);
    }
}
