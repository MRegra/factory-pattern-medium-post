package org.example.config;

import org.example.domain.PaymentGateway;
import org.example.domain.PaymentProcessor;
import org.example.domain.PaypalGateway;
import org.example.domain.StripeGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class demonstrates the Factory pattern in Spring:
 * - @Bean methods are factory methods.
 * - The ApplicationContext/BeanFactory calls these to create beans.
 */
@Configuration
public class PaymentConfig {

    @Bean
    public PaymentGateway paymentGateway(@Value("${payment.gateway}") String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing or empty payment.gateway property");
        }
        return switch (type.toLowerCase()) {
            case "stripe" -> new StripeGateway();
            case "paypal" -> new PaypalGateway();
            default -> throw new IllegalArgumentException("Unknown gateway: " + type);
        };
    }

    @Bean
    public PaymentProcessor paymentProcessor(PaymentGateway gateway) {
        return new PaymentProcessor(gateway);
    }
}
