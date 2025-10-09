package org.factory.pattern.domain;

public class StripeGateway implements PaymentGateway {
    @Override
    public void charge(double amount) {
        System.out.println("[Stripe] Charging " + amount);
    }

    @Override
    public String name() {
        return "stripe";
    }
}
