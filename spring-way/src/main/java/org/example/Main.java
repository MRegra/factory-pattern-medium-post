package org.example;

import org.example.domain.PaymentProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args); // Run your main configuration class
    }

    // Runner executes after Spring finishes startup
    @Bean
    public CommandLineRunner run(PaymentProcessor processor) {
        return args -> {
            processor.process(49.99); // or any test logic
        };
    }
}