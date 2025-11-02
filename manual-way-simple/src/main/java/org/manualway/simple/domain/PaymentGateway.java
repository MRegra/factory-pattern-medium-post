package org.manualway.simple.domain;

public interface PaymentGateway {
    void charge(double amount);
    String name();
}
