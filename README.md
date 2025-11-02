# Factory Pattern — Manual DI vs Spring Boot

[![Build and Test](https://github.com/MRegra/factory-pattern-medium-post/actions/workflows/test.yml/badge.svg)](https://github.com/MRegra/factory-pattern-medium-post/actions/workflows/test.yml)

Learn the **Factory Design Pattern** by first implementing it **manually** (including a tiny DI container), then mapping the exact concepts to **Spring Boot** (with `@Configuration`/`@Bean`). This repo is designed as both a learning resource and a runnable demo.

---

## Repo Structure

    factory-pattern-medium-post/
    ├─ manual-way/ # Plain Java: Factory + tiny DI container
    └─ spring-way/ # Spring Boot: configuration-driven wiring


---

## Requirements

- **Java 21** (Temurin/Zulu/Oracle all fine)
- **Maven 3.9+**

> Verify:
> ```bash
> java -version
> mvn -version
> ```

---

## Quick Start

### 1) Run tests (both modules)
```bash
# from repo root (matrix build in CI does the same)
cd manual-way && mvn clean verify
cd ../spring-way && mvn clean verify

```

### 2) Run the Spring app
```bash
cd spring-way
mvn spring-boot:run
```

Now watch the console output. You can switch the active gateway via:

    spring-way/src/main/resources/application.properties
    payment.gateway=stripe   # or: paypal

Expected console:

    Using gateway: stripe
    [Stripe] Charging $49.99

---

# Factory Pattern — Three Clear Variants

This refactor provides **three self-contained implementations**, each with its own package and entry point:

```
refactored/
  manual-simple/
  manual-factory/
  spring/
```

## 1) manual-simple
- **Package:** `com.example.payments.manualsimple`
- **Idea:** Direct `new` calls and simple `if/else` choosing Stripe or PayPal.
- **Entry:** `com.example.payments.manualsimple.Main`
- **Run (example):**
    - Compile: `javac -d out $(find src/main/java -name "*.java")`
    - Run: `java -cp out com.example.payments.manualsimple.Main stripe`
    - Or: `java -cp out com.example.payments.manualsimple.Main paypal`

## 2) manual-factory
- **Package:** `com.example.payments.manualfactory`
- **Idea:** Centralize creation in `PaymentGatewayFactory`, choose via `application.properties`.
- **Entry:** `com.example.payments.manualfactory.Main`
- **Run:**
    - Compile: `javac -d out $(find src/main/java -name "*.java")`
    - Copy resources to classpath root: `cp -r src/main/resources/* out/`
    - Run: `java -cp out com.example.payments.manualfactory.Main`
    - Change gateway: edit `src/main/resources/application.properties`

## 3) spring
- **Package:** `com.example.payments.springway`
- **Idea:** Use Spring to instantiate beans via `@Configuration + @Bean` and inject them.
- **Entry:** `com.example.payments.springway.Main`
- **Run (requires Spring Boot deps in a build tool like Maven/Gradle):**
    - Add Spring Boot (`spring-boot-starter`) to your build.
    - Set `payment.gateway=stripe` or `paypal` in `src/main/resources/application.properties`.
    - Run the app; Spring wires `PaymentGateway` into `PaymentProcessor` for you.

## Concept Mapping

| Concern | Manual-simple | Manual-factory | Spring |
|---|---|---|---|
| Creation site | Scattered (every use site) | Centralized factory | `@Bean` methods in config |
| Selection logic | `if/else` by arg | `Properties` (`application.properties`) | `@Value("${payment.gateway...}")` |
| DI | Manual via constructor | Manual via constructor | Automated constructor injection |
| Extensibility | Hard | Easy (add branch) | Easiest (add bean or profile) |
| Testability | OK but noisy | Better | Best (mock beans) |

## Notes
- Each variant is **self-contained**—no cross-dependencies.
- Names are consistent across variants so your article can compare files 1:1.
- You can copy/paste snippets straight into your Medium post.
