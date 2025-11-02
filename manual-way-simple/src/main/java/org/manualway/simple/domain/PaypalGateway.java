package org.manualway.simple.domain;

public class PaypalGateway implements PaymentGateway {
    @Override
    public void charge(double amount) {
        System.out.println("[PayPal] Charging " + amount);
    }

    @Override
    public String name() {
        return "paypal";
    }
}
