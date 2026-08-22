package com.cuseto.pong.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import com.cuseto.pong.config.schema.AppConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public final class ConfigLoader {
    private static final String DEFAULT_CONFIG_RESOURCE = "/config.yaml";
    static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory())
        .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
        .enable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES);

    private ConfigLoader() {
    }

    public static AppConfig load() {
        return load(DEFAULT_CONFIG_RESOURCE);
    }

    public static AppConfig load(String resourcePath) {
        try (InputStream input = ConfigLoader.class.getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException(
                    "Configuration resource was not found: " + resourcePath
                );
            }
            return MAPPER.readValue(input, AppConfig.class);
        }
        catch (IOException exception) {
            throw new UncheckedIOException(
                "Could not load configuration resource: " + resourcePath,
                exception
            );
        }
    }
}
