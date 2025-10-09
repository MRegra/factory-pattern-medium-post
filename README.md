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
