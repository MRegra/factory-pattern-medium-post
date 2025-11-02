package org.example.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class PaymentProcessor {
    private final PaymentGateway gateway;

    public PaymentProcessor(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void process(double amount) {
        log.info("Using gateway: {}", gateway.name());
        gateway.charge(amount);
    }

}
