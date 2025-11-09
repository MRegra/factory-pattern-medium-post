package org.factory.domain;

public interface PaymentGateway {
    void charge(double amount);
    String name();
}
