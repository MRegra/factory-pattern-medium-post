package org.example.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StripeGateway implements PaymentGateway {

    @Override
    public void charge(double amount) {
        log.info("[Stripe] Charging {}", amount);
    }

    @Override
    public String name() {
        return "stripe";
    }
}
