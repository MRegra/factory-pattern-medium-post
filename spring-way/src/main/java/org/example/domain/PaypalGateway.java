package org.example.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaypalGateway implements PaymentGateway {
    @Override
    public void charge(double amount) {
        log.info("[PayPal] Charging {}", amount);
    }
    @Override
    public String name() {
        return "paypal";
    }
}
