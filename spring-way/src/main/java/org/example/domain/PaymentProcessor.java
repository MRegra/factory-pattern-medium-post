package org.example.domain;

import org.springframework.stereotype.Component;

@Component
public class PaymentProcessor {
    private final PaymentGateway gateway;

    public PaymentProcessor(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void process(double amount) {
        System.out.println("Using gateway: " + gateway.name());
        gateway.charge(amount);
    }

    public PaymentGateway getGateway() {
        return gateway;
    }
}