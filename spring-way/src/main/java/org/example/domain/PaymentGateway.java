package org.example.domain;

public interface PaymentGateway {
    void charge(double amount);
    String name();
}
