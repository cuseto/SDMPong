package com.cuseto.pong.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import com.cuseto.pong.config.schema.AppConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Loads {@link AppConfig} from a bundled YAML resource on the classpath.
 *
 * <p>Unlike {@link ConfigRepository}, this always reads from the
 * application's packaged resources rather than the user's filesystem, and
 * fails fast: unknown, missing, or {@code null} properties in the YAML are
 * rejected rather than defaulted. {@link ConfigRepository} falls back to
 * this loader's defaults when no user configuration is present or the user
 * configuration is invalid.
 */
public final class ConfigLoader {
    private static final String DEFAULT_CONFIG_RESOURCE = "/config.yaml";
    static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory())
        .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
        .enable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES);

    private ConfigLoader() {
    }

    /**
     * Loads the application's bundled default configuration.
     *
     * @return the configuration read from the bundled {@code config.yaml} resource
     * @throws IllegalArgumentException if the bundled resource is missing
     * @throws UncheckedIOException if the resource cannot be read or parsed
     */
    public static AppConfig load() {
        return load(DEFAULT_CONFIG_RESOURCE);
    }

    /**
     * Loads configuration from the given classpath resource.
     *
     * @param resourcePath the classpath location of the YAML resource to load, e.g. {@code "/config.yaml"}
     * @return the configuration read from {@code resourcePath}
     * @throws IllegalArgumentException if no resource exists at {@code resourcePath}
     * @throws UncheckedIOException if the resource cannot be read or parsed
     */
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
