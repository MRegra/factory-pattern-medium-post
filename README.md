# Factory Pattern — From Manual DI to Spring Boot

[![Build and Test](https://github.com/MRegra/factory-pattern-medium-post/actions/workflows/test.yml/badge.svg)](https://github.com/MRegra/factory-pattern-medium-post/actions/workflows/test.yml)

Learn the **Factory Design Pattern** and how it naturally evolves into **Dependency Injection (DI)** — first by implementing it manually (even building your own tiny DI container), then by mapping those exact concepts to **Spring Boot**’s `@Configuration` and `@Bean` system.

This repo complements the Medium post 
**[Factory Pattern: “How Spring Creates Your Beans”](https://medium.com/@mregra/factory-pattern-how-spring-creates-your-beans)**
and serves as a runnable, educational demo.

---

## What You’ll Learn

Each module builds on the previous one:

| Stage                                  | Module                                    | What It Teaches                                                         |
| -------------------------------------- | ----------------------------------------- | ----------------------------------------------------------------------- |
| **1️⃣ Manual-Way-Simple**              | [`manual-way-simple/`](manual-way-simple) | The pain of manual wiring using plain `new` calls.                      |
| **2️⃣ Manual-Way (Factory + Tiny DI)** | [`manual-way/`](manual-way)               | Introduces the Factory Pattern and a miniature DI container.            |
| **3️⃣ Spring-Way**                     | [`spring-way/`](spring-way)               | Replaces your manual wiring with Spring Boot’s declarative bean system. |

Each stage is a self-contained Java app showing how abstraction, configuration, and injection evolve — step by step.

---

## Requirements

* **Java 21+** (Temurin, Zulu, or Oracle all work)
* **Maven 3.9+**

Verify:

```bash
java -version
mvn -version
```

---

## Quick Start

### Run all tests

```bash
cd manual-way-simple && mvn clean verify
cd ../manual-way && mvn clean verify
cd ../spring-way && mvn clean verify
```

### Run the Spring Boot app

```bash
cd spring-way
mvn spring-boot:run
```

You’ll see output like:

```
Using gateway: stripe
[Stripe] Charging 49.99
```

To switch gateways:

```properties
# spring-way/src/main/resources/application.properties
payment.gateway=paypal
```

Or override via command line:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--payment.gateway=paypal"
```

---

## Concept Evolution

| Concern              | Manual-Simple             | Manual-Way (Factory + DI)       | Spring-Way (Full DI)                    |
| -------------------- | ------------------------- | ------------------------------- | --------------------------------------- |
| Object Creation      | Scattered `new` calls     | Centralized factory             | `@Bean` methods managed by Spring       |
| Configuration        | Hardcoded                 | Property-driven (custom config) | Property-driven (Spring `@Value`)       |
| Dependency Injection | Manual constructor wiring | Tiny custom container           | Automatic injection                     |
| Extensibility        | Low                       | Medium                          | High (Profiles, Conditions, Qualifiers) |
| Lifecycle            | Manual                    | Partially managed               | Fully managed                           |
| Testability          | Basic                     | Better                          | Excellent (mock beans, test slices)     |
| Maintainability      | Painful                   | Tolerable                       | Effortless                              |

---

## Project Layout

```
factory-pattern-medium-post/
├─ manual-way-simple/   # Step 1 – Direct object creation
│   └── src/main/java/org/manualway/simple/...
│
├─ manual-way/          # Step 2 – Factory + mini DI container
│   └── src/main/java/org/factory/pattern/...
│
└─ spring-way/          # Step 3 – Spring Boot with @Bean + @Configuration
    └── src/main/java/org/example/...
```

---

## How This Maps to Spring

| Concept                | Manual Implementation             | Spring Equivalent                 |
| ---------------------- | --------------------------------- | --------------------------------- |
| Factory class          | `PaymentGatewayFactory`           | `@Bean` method in `PaymentConfig` |
| DI container           | `MyApplicationContext`            | `ApplicationContext`              |
| Singleton cache        | HashMap in `MyApplicationContext` | Spring-managed scopes             |
| Config properties      | `app.properties`                  | `application.properties`          |
| Custom injection logic | Constructor recursion             | Automatic wiring & reflection     |
| Default fallback       | Custom factory logic              | `@ConditionalOnMissingBean`       |

---

## Learning Flow

1. **manual-way-simple** — The naive version.
   Direct `new` calls and hardcoded logic. Painful, but foundational.

2. **manual-way** — Introduces a **Factory** and a **tiny DI container** that mimics Spring.
   You’ll handle constructor injection, singletons, and config-driven creation.

3. **spring-way** — The real thing.
   Spring manages object creation, configuration, and lifecycle for you via annotations.

---

## Related Reading

**Medium Article:**
[Factory Pattern: “How Spring Creates Your Beans”](https://medium.com/@mregra/factory-pattern-how-spring-creates-your-beans)

The post walks through these three modules conceptually — explaining *why* each exists, the *pain points* it solves, and how they build toward Spring Boot’s DI system.

---

## Contributing

This repo is meant for **learning and teaching**.
If you spot improvements (tests, code clarity, or explanations), feel free to open a PR or comment on the Medium post.

---

## License

MIT — free to use, modify, and share for educational or commercial purposes.

---

**Created by [Marcelo Regra da Silva](https://medium.com/@mregra)**

> “You built the magic manually. Now you understand how Spring does it — elegantly, invisibly, and at scale.”