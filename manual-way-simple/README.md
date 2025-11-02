# Manual Way — Simple Version

This module represents the **“before”** stage in our journey toward clean, scalable object creation.
It shows how a naïve implementation of object wiring quickly becomes painful as your project grows.

---

## What This Module Does

We’re simulating a simple **payment system** that supports two gateways: **Stripe** and **PayPal**.

In this version, each dependency is **manually created** (using `new`) inside the `Main` class.

### Structure

```
manual-way-simple/
└── src/
    ├── main/java/org/manualway/simple/
    │   ├── Main.java
    │   └── domain/
    │       ├── PaymentGateway.java
    │       ├── PaypalGateway.java
    │       ├── StripeGateway.java
    │       └── PaymentProcessor.java
    └── test/java/... (JUnit 5 tests)
```

### Core Idea

* **`PaymentGateway`** — defines a contract with `charge()` and `name()` methods.
* **`StripeGateway` / `PaypalGateway`** — concrete implementations of that interface.
* **`PaymentProcessor`** — depends on a `PaymentGateway`, not knowing which one.
* **`Main`** — chooses which gateway to use, creates all objects manually, and runs the process.

---

## Example Run

```bash
# Compile & run via Maven Exec Plugin
mvn -q exec:java                   # Default (stripe)
mvn -q -Dexec.args=paypal exec:java
```

### Output

```
Using gateway: paypal
[PayPal] Charging 49.99
```

or

```
Using gateway: stripe
[Stripe] Charging 49.99
```

---

## The Implementation (Explained)

### 1. The Contract

```java
public interface PaymentGateway {
    void charge(double amount);
    String name();
}
```

Every payment method must implement this interface — it’s the **abstraction layer**.

---

### 2. The Implementations

```java
public class StripeGateway implements PaymentGateway {
    public void charge(double amount) { System.out.println("[Stripe] Charging " + amount); }
    public String name() { return "stripe"; }
}
```

```java
public class PaypalGateway implements PaymentGateway {
    public void charge(double amount) { System.out.println("[PayPal] Charging " + amount); }
    public String name() { return "paypal"; }
}
```

Each gateway knows only how to perform its own operation — simple and isolated.

---

### 3. The Processor

```java
public class PaymentProcessor {
    private final PaymentGateway gateway;

    public PaymentProcessor(PaymentGateway gateway) { this.gateway = gateway; }

    public void process(double amount) {
        System.out.println("Using gateway: " + gateway.name());
        gateway.charge(amount);
    }
}
```

The `PaymentProcessor` depends only on the interface.
It doesn’t care whether it’s Stripe or PayPal — that’s decided elsewhere.

---

### 4. The Main Pain Point

```java
public class Main {
    static void main(String[] args) {
        String type = (args.length > 0 ? args[0] : "stripe").toLowerCase();

        PaymentGateway gateway;
        if (type.equals("paypal")) {
            gateway = new PaypalGateway();
        } else if (type.equals("stripe")) {
            gateway = new StripeGateway();
        } else {
            throw new IllegalArgumentException("Unsupported payment gateway type: " + type);
        }

        PaymentProcessor processor = new PaymentProcessor(gateway);
        processor.process(49.99);
    }
}
```

At first glance, it’s simple and clean…
But this is exactly where the **pain starts**.

---

## The Pain of Manual Wiring

1. **Tight Coupling Everywhere**

    * `Main` directly depends on every concrete implementation.
    * If you add a new gateway (`ApplePayGateway`), you must edit every place that chooses gateways.

2. **Scattered Creation Logic**

    * The logic deciding *which implementation to use* is duplicated across the codebase.
    * Each new feature risks introducing inconsistencies (“forgot to switch one to PayPal”).

3. **Harder Testing**

    * You can’t easily replace `PaypalGateway` with a mock in tests — you always create real objects.

4. **Zero Configuration Flexibility**

    * Changing from Stripe to PayPal means changing source code and redeploying.
    * You can’t configure it externally (like via `application.properties`).

5. **No Lifecycle Management**

    * You have to manually ensure singletons, caching, or destruction logic if needed.
    * This becomes chaos in larger systems with dozens of dependencies.

---

## What Comes Next — `manual-way` (Factory Pattern)

To solve this, we introduce a **Factory Pattern** in the next module:

✅ Centralizes creation logic into a single `PaymentGatewayFactory`.

✅ Removes `if/else` clutter from the rest of the app.

✅ Makes switching implementations as simple as changing one configuration property.

✅ Paves the way for Spring’s `@Bean` and dependency injection system.

---

Example preview from the next step:

```java
public class PaymentGatewayFactory {
    private final Properties props;
    public PaymentGatewayFactory(Properties props) { this.props = props; }

    public PaymentGateway create() {
        String type = props.getProperty("payment.gateway", "stripe");
        return switch (type.toLowerCase()) {
            case "stripe" -> new StripeGateway();
            case "paypal" -> new PaypalGateway();
            default -> throw new IllegalArgumentException("Unknown gateway: " + type);
        };
    }
}
```

In other words:

> The Factory centralizes creation — the first real step toward dependency injection.

---

## Summary

| Aspect          | Manual Way (Simple) | Manual Way (Factory)      | Spring Way                    |
| --------------- | ------------------- | ------------------------- | ----------------------------- |
| Object creation | Manual `new` calls  | Centralized factory       | Automatic via DI              |
| Configuration   | Hardcoded           | Configurable (properties) | Configurable (Spring context) |
| Coupling        | High                | Lower                     | Minimal                       |
| Scalability     | Poor                | Better                    | Excellent                     |
| Readability     | Simple but fragile  | More structured           | Clean and declarative         |

---

### Takeaway

This version is intentionally **painful** — it forces you to see *why* dependency injection exists.
When your codebase grows beyond a few classes, managing all those `new` calls manually becomes a nightmare.

That’s where the **Factory Pattern** and later **Spring Beans** step in to automate, centralize, and standardize the entire process.