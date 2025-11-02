package org.example.config;

import org.example.domain.PaymentGateway;
import org.example.domain.PaymentProcessor;
import org.example.domain.PaypalGateway;
import org.example.domain.StripeGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * In Spring, @Bean methods are factory methods. The container calls these
 * to create and wire your objects.
 */
@Configuration
public class PaymentConfig {

    @Bean
    @ConditionalOnMissingBean(PaymentGateway.class)
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
