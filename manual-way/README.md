## Manual Way — Factory Pattern + Tiny Beans

This module upgrades the simple baseline by introducing:

1. A **Factory Pattern** to centralize creation of `PaymentGateway`
2. A tiny **DI container (Dependency Injection)** (“beans”) to handle **constructor injection** and **singletons**.

It’s still plain Java — no Spring yet — but the code is already far more scalable and configurable than the naive approach.

---

## What This Module Does

* Uses `app.properties` (`payment.gateway=stripe|paypal`) to choose the gateway.
* A **factory** (`PaymentGatewayFactory`) reads the config and returns the right implementation.
* A **minimal ApplicationContext**:

    * Caches singletons
    * Performs constructor injection (chooses the longest constructor)
    * Allows registering beans either **by type** or via a **Supplier** (custom factory)

### Structure (core files)

```
src/main/java/
  org/factory/pattern/
    Main.java
  org/factory/pattern/dependencyinjection/
    BeanDefinition.java
    Config.java
    MyApplicationContext.java
  org/factory/pattern/domain/
    PaymentGateway.java
    PaymentProcessor.java
    PaypalGateway.java
    StripeGateway.java
  org/factory/pattern/factory/
    PaymentGatewayFactory.java

src/main/resources/
  app.properties
```

---

## Why This Design

### Pain Recap (from manual-way-simple)

* Creation logic was **scattered** (everywhere we needed a gateway).
* Code was **tightly coupled** to concrete classes.
* Switching implementations required **source changes**.
* Testing was harder because of **hardwired `new` calls**.

### What We Fixed Here

* ✅ **Centralized** creation in `PaymentGatewayFactory`
* ✅ **Configuration-driven** selection (`app.properties` or `-Dpayment.gateway`)
* ✅ **Constructor injection** via a tiny DI container (`MyApplicationContext`)
* ✅ **Clear responsibilities**: business code uses **interfaces**, factory decides **which class**

---

## How It Works

### 1) Factory (centralized decision)

```java
public class PaymentGatewayFactory {
    private final Config config;
    public PaymentGatewayFactory(Config config) { this.config = config; }

    public PaymentGateway create() {
        String type = config.getOrDefault("payment.gateway", "stripe");
        return switch (type.toLowerCase()) {
            case "paypal" -> new PaypalGateway();
            case "stripe" -> new StripeGateway();
            default -> throw new IllegalArgumentException("Unknown gateway: " + type);
        };
    }
}
```

### 2) Tiny DI container (beans)

```java
// Register bean by type (constructor injection)
ctx.registerBean(PaymentProcessor.class);

// Register bean with a custom supplier (factory hook)
ctx.registerBean(PaymentGateway.class, () -> new PaymentGatewayFactory(config).create());
```

The container:

* **Singletons:** caches one instance per type
* **Injection:** resolves constructor parameters recursively
* **Factories:** if you register a Supplier, the container delegates creation to it

### 3) Entry point

```java
PaymentProcessor processor = ctx.getBean(PaymentProcessor.class);
processor.process(49.99);
```

---

## Run it

**Maven:**

```bash
mvn -q clean package
mvn -q exec:java
```

**Switch gateway (no code changes!):**

```bash
# via properties file
# src/main/resources/app.properties
payment.gateway=paypal

# OR override at runtime:
mvn -q "-Dpayment.gateway=paypal" exec:java
```

Expected output:

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

## Testing

* Unit test `PaymentGatewayFactory` by passing a fake `Config`.
* Unit test `PaymentProcessor` with a stub `PaymentGateway` (like in the simple module).
* (Optional) Unit test `MyApplicationContext` with a tiny “toy” graph to verify constructor injection.

---

## What This Sets Up (Next Step → Spring)

We’re now thinking in **beans** and **factories**. Spring will do exactly this for you — but with:

* Advanced scopes (singleton, prototype)
* Conditional beans, profiles, property binding
* Lifecycle hooks
* A richer, type-safe configuration model

In the next module, you’ll replace this custom container with:

* `@Configuration` + `@Bean` methods
* `@Value("${payment.gateway:stripe}")`
* Autowired constructor parameters

Same idea — just industrial strength.

---

## Takeaway

The **Factory Pattern** eliminates scattered `new` calls and centralizes decisions.
The **tiny DI container** brings constructor injection and singletons.
Together, they deliver a **cleaner, configurable**, and **testable** design — a perfect stepping stone to Spring.
