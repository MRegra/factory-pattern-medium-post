package org.factory.pattern.dependencyinjection;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Minimal Dependency Injection container:
 * - getBean(Class<T>) creates singletons
 * - constructor injection (pick the longest constructor)
 * - delegates some decisions to your own factories (e.g., PaymentGatewayFactory)
 */
public class MyApplicationContext {

    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, BeanDefinition<?>> definitions = new HashMap<>();
    private final Map<Class<?>, Supplier<?>> suppliers = new HashMap<>();
    private final Config config;

    public MyApplicationContext(Config config) {
        this.config = config;
    }

    public <T> void registerBean(Class<T> type) {
        definitions.put(type, new BeanDefinition<>(type));
    }

    /**
     * Register a bean backed by a Supplier (i.e., a custom factory)
     */
    public <T> void registerBean(Class<T> type, Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier");
        suppliers.put(type, supplier);
        definitions.put(type, new BeanDefinition<>(type));
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        if (singletons.containsKey(type)) {
            return (T) singletons.get(type);
        }
        if (!definitions.containsKey(type)) {
            throw new IllegalStateException("No bean definition for " + type.getName());
        }

        T instance;
        Supplier<?> supplier = suppliers.get(type);
        if (supplier != null) {
            instance = (T) supplier.get();
        } else {
            instance = createViaConstructor(type);
        }

        singletons.put(type, instance);
        return instance;
    }

    @SuppressWarnings("unchecked")
    private <T> T createViaConstructor(Class<T> type) {
        try {
            Constructor<?>[] ctors = type.getConstructors();
            if (ctors.length == 0) {
                throw new IllegalStateException("No public constructor for " + type.getName());
            }
            // choose longest-arg constructor (simple heuristic)
            Constructor<?> target = ctors[0];
            for (Constructor<?> c : ctors) {
                if (c.getParameterCount() > target.getParameterCount()) target = c;
            }

            Class<?>[] paramTypes = target.getParameterTypes();
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                registerIfAbsent(paramTypes[i]);
                args[i] = getBean(paramTypes[i]);
            }
            return (T) target.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create " + type.getName() + ": " + e.getMessage(), e);
        }
    }

    private void registerIfAbsent(Class<?> type) {
        if (!definitions.containsKey(type)) {
            definitions.put(type, new BeanDefinition<>(type));
        }
    }

    public Config getConfig() {
        return config;
    }
}
