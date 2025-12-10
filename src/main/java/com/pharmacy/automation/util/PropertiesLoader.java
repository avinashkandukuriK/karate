package com.pharmacy.automation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Utility for loading {@link Properties} files from the classpath.
 */
public final class PropertiesLoader {

    private PropertiesLoader() {
    }

    public static Properties load(String resourcePath) {
        Objects.requireNonNull(resourcePath, "resourcePath is required");
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IllegalArgumentException("Unable to find properties resource: " + resourcePath);
            }
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read properties resource: " + resourcePath, e);
        }
    }

    public static Map<String, String> loadAsMap(String resourcePath) {
        Properties properties = load(resourcePath);
        Map<String, String> map = new HashMap<>();
        for (String name : properties.stringPropertyNames()) {
            map.put(name, properties.getProperty(name));
        }
        return Collections.unmodifiableMap(map);
    }
}
