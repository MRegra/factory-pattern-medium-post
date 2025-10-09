package org.factory.pattern.domain;

public interface PaymentGateway {
    void charge(double amount);
    String name();
}
