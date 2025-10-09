package org.factory.pattern.dependencyinjection;

import org.factory.pattern.domain.PaymentGateway;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Minimal Dependency Injection container:
 * - getBean(Class<T>) creates singletons
 * - constructor injection (pick the longest constructor)
 * - delegates some decisions to your own factories (e.g., PaymentGatewayFactory)
 */
public class MyApplicationContext {

    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, BeanDefinition<?>> definitions = new HashMap<>();
    private final Config config;

    public MyApplicationContext(Config config) {
        this.config = config;
    }

    public <T> void registerBean(Class<T> type) {
        definitions.put(type, new BeanDefinition<>(type));
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        // Singleton cache
        if (singletons.containsKey(type)) {
            return (T) singletons.get(type);
        }

        BeanDefinition<T> def = (BeanDefinition<T>) definitions.get(type);
        if (def == null) {
            throw new IllegalStateException("No bean definition for " + type);
        }

        T instance = create(def.type());
        singletons.put(type, instance);
        return instance;
    }

    @SuppressWarnings("unchecked")
    private <T> T create(Class<T> type) {
        try {
            if (type == PaymentGateway.class) {
                // delegate to your custom factory decision
                Class<?> factoryClass = Class.forName("org.factory.pattern.factory.PaymentGatewayFactory");
                Constructor<?> factoryCtor = factoryClass.getConstructor(Config.class);
                Object factory = factoryCtor.newInstance(config);
                Method method = factoryClass.getMethod("create");
                return (T) method.invoke(factory);
            }

            // Otherwise: constructor injection on the longest constructor
            Constructor<?>[] ctors = type.getConstructors();
            if (ctors.length == 0) {
                throw new IllegalStateException("No public constructor for " + type);
            }
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
            throw new RuntimeException("Failed to create " + type + ": " + e.getMessage(), e);
        }
    }

    private void registerIfAbsent(Class<?> type) {
        if (!definitions.containsKey(type)) {
            definitions.put(type, new BeanDefinition<>(type));
        }
    }
}
