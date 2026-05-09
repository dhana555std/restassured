package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigLoader {
    private static final Properties properties;

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            properties = new Properties();
            properties.load(input);
            log.info("config.properties loaded successfully");
        } catch (IOException e) {
            log.error("Failed to load config.properties", e);
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * Retrieves a configuration value with the following priority:
     * 1. System property (-D flag)
     * 2. Environment variable
     * 3. config.properties file
     *
     * @param key the configuration key
     * @return the configuration value
     * @throws IllegalArgumentException if the key is not found in any source
     */
    public static String getConfig(String key) {
        // Check system property first (-D flag)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.trim().isEmpty()) {
            log.info("Using system property for key: {}", key);
            return systemProperty;
        }

        // Check environment variable
        String envVariable = System.getenv(key);
        if (envVariable != null && !envVariable.trim().isEmpty()) {
            log.info("Using environment variable for key: {}", key);
            return envVariable;
        }

        // Fall back to properties file
        String propertyValue = properties.getProperty(key);
        if (propertyValue != null && !propertyValue.trim().isEmpty()) {
            log.info("Using config.properties for key: {}", key);
            return propertyValue;
        }

        // If not found anywhere, log error and throw exception
        log.error("Configuration key '{}' not found in system properties, environment variables, or config.properties", key);
        throw new IllegalArgumentException("Configuration key '" + key + "' not found");
    }

    public static String getBaseUrl() {
        return getConfig("baseUrl");
    }
}

