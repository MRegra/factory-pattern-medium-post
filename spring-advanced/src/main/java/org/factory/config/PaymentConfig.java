package org.factory.config;

import org.factory.domain.PaymentGateway;
import org.factory.domain.PaymentProcessor;
import org.factory.domain.PaypalGateway;
import org.factory.domain.StripeGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * In Spring, @Bean methods are factory methods. The container calls these
 * to create and wire your objects.
 */
@Configuration
public class PaymentConfig {

    @Bean
    public PaymentGateway paymentGateway(@Value("${payment.gateway:STRIPE}") GatewayType type) {
        return switch (type) {
            case PAYPAL -> new PaypalGateway();
            case STRIPE -> new StripeGateway();
        };
    }

    /**
     * Factory method for PaymentProcessor
     * * Spring is calling it automatically during startup when it builds the ApplicationContext.
     */
    @Bean
    public PaymentProcessor paymentProcessor(PaymentGateway gateway) {
        return new PaymentProcessor(gateway);
    }
}

