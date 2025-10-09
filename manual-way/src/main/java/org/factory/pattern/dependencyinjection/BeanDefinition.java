package org.factory.pattern.dependencyinjection;

public record BeanDefinition<T>(Class<T> type) {}