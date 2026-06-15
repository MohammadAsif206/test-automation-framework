package com.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads config from system properties first, then .env.properties, then returns the default.
 * Priority: system property > env variable > properties file > hardcoded default
 */
public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static final Properties props = new Properties();

    static {
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            log.warn("config.properties not found on classpath — using env/system properties only");
        }
    }

    private ConfigManager() {}

    public static String get(String key, String defaultValue) {
        // 1. system property (-DBROWSER=firefox)
        String val = System.getProperty(key);
        if (val != null && !val.isBlank()) return val;

        // 2. environment variable
        val = System.getenv(key);
        if (val != null && !val.isBlank()) return val;

        // 3. properties file
        val = props.getProperty(key);
        if (val != null && !val.isBlank()) return val;

        return defaultValue;
    }

    public static String getBaseUrl() {
        return get("BASE_URL", "https://www.saucedemo.com");
    }
}
