package org.factory.pattern.dependencyinjection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties props = new Properties();

    public Config(String classpathResource) {
        try (InputStream in = getClass().getResourceAsStream("/" + classpathResource)) {
            if (in == null) throw new IllegalArgumentException("No resource: " + classpathResource);
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }
}
