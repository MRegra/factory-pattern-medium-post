# Spring Way — Let the Framework Do the Heavy Lifting

This module shows how **Spring** eliminates all the manual factory and container boilerplate from the previous module.
Instead of building and wiring objects yourself, you simply define **`@Bean` factory methods**, and Spring’s `ApplicationContext` takes care of creation, dependency injection, and lifecycle management automatically.

---

## What This Module Does

* Reads configuration (`payment.gateway=stripe|paypal`).
* Exposes two beans:

    * `PaymentGateway` → selected dynamically via configuration.
    * `PaymentProcessor` → automatically receives the injected gateway.
* Runs a Spring Boot application that executes a small demo using `CommandLineRunner`.

### Structure

```
src/main/java/
  org/example/
    Main.java
  org/example/config/
    PaymentConfig.java
    (optional) PaymentConfigProfiles.java
    GatewayType.java
  org/example/domain/
    PaymentGateway.java
    PaymentProcessor.java
    PaypalGateway.java
    StripeGateway.java

src/main/resources/
  application.properties
```

---

## How It Works

### 1) `@Bean` Methods Act as Factories

```java
@Configuration
class PaymentConfig {
  @Bean
  @ConditionalOnMissingBean(PaymentGateway.class)
  PaymentGateway paymentGateway(@Value("${payment.gateway:STRIPE}") GatewayType type) {
      return switch (type) {
          case PAYPAL -> new PaypalGateway();
          case STRIPE -> new StripeGateway();
      };
  }

  @Bean
  PaymentProcessor paymentProcessor(PaymentGateway gateway) {
      return new PaymentProcessor(gateway);
  }
}
```

* Each `@Bean` method is a **factory function**.
* Spring calls these methods automatically at startup, registers the returned objects as beans, and injects them wherever they’re needed.

---

### 2) Why We Keep Both `@Bean` Methods

#### `PaymentProcessor`

Even if your IDE says *“method never used”*, Spring **is** using it at runtime.
It scans your configuration classes, finds `@Bean` methods, and **calls them via reflection** when building the application context.

That’s how your `Main` class can do this:

```java
@Bean
public CommandLineRunner run(PaymentProcessor processor) {
    return args -> processor.process(49.99);
}
```

Spring injects a `PaymentProcessor` automatically — because it was created by that `@Bean` method.

So although it’s never *directly* called by your code, it’s essential for Spring’s dependency injection to work.

#### `PaymentGateway`

This method decides **which gateway** to use (Stripe or PayPal) based on your configuration.
You could use profiles instead (see below), but this property-based version keeps things simple and flexible.

---

### 3) What `@ConditionalOnMissingBean` Does

`@ConditionalOnMissingBean` tells Spring:

> “Only create this bean **if no other bean of the same type** exists.”

That means:

* If no `PaymentGateway` bean exists → create one using this method.
* If another config (like a profile-based `@Bean`) already provided one → skip this method.

This makes your configuration **composable**.
For example, you can define another class like this:

```java
@Configuration
public class PaymentConfigProfiles {
  @Bean @Profile("paypal")
  public PaymentGateway paypal() { return new PaypalGateway(); }

  @Bean @Profile("stripe")
  public PaymentGateway stripe() { return new StripeGateway(); }
}
```

If you run with:

```bash
mvn -q spring-boot:run -Dspring-boot.run.profiles=paypal
```

…the profile-based bean is used, and the `PaymentConfig` method is ignored.
That’s exactly what `@ConditionalOnMissingBean` enables — **graceful fallback without conflicts**.

---

### 4) Property-Driven Selection

Set your gateway in `application.properties`:

```properties
payment.gateway=stripe
```

Override it at runtime:

```bash
mvn -q spring-boot:run -Dspring-boot.run.arguments="--payment.gateway=paypal"
# or
java -jar app.jar --payment.gateway=paypal
```

Spring automatically injects the correct implementation — no code changes needed.

---

### 5) Focus on the *What*, Not the *How*

No factories, no `new`, no manual wiring.
You simply declare relationships — Spring manages object creation, lifecycle, and dependency resolution behind the scenes.

---

## Why This Beats Manual Approaches

| Concern       | Manual-simple   | Manual-way (Factory + Tiny DI) | Spring-way                               |
| ------------- | --------------- | ------------------------------ | ---------------------------------------- |
| Creation      | Scattered `new` | Central factory                | `@Bean` factory methods                  |
| Wiring        | Manual          | Tiny container                 | Auto-wired by Spring                     |
| Config        | Hardcoded       | Properties (custom)            | Properties with full binding support     |
| Extensibility | Low             | Medium                         | High (profiles, conditions, qualifiers)  |
| Lifecycle     | Manual          | Manual-ish                     | Managed automatically                    |
| Testability   | OK              | Better                         | Excellent (slices, mocks, context reuse) |

---

## Running the Project

* **Default (Stripe):**

  ```bash
  mvn -q spring-boot:run
  ```
* **PayPal (property override):**

  ```bash
  mvn -q spring-boot:run -Dspring-boot.run.arguments="--payment.gateway=paypal"
  ```
* **Profile-based (alternative mode):**

  ```bash
  mvn -q spring-boot:run -Dspring-boot.run.profiles=paypal
  ```
* **Runnable JAR:**

  ```bash
  mvn -q clean package
  java -jar target/<artifact>.jar --payment.gateway=paypal
  ```

---

### Expected Output

```
Using gateway: stripe
[Stripe] Charging 49.99
```

or

```
Using gateway: paypal
[PayPal] Charging 49.99
```

---

## TL;DR

In the previous module, you *implemented* dependency injection manually.
In this one, you *delegate* it to Spring — a mature framework that:

* Handles bean creation, wiring, and configuration for you.
* Supports safe enum-based configuration.
* Lets you override or extend behavior via conditions and profiles.

Cleaner, safer, and ready for production — with **zero boilerplate**.