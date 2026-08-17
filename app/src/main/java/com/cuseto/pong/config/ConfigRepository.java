package com.cuseto.pong.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.cuseto.pong.config.schema.AppConfig;

/** Loads external config or fallbacks on bundled defaults */
public final class ConfigRepository {
    private static final String USER_CONFIG_DIRECTORY = ".sdm-pong";
    private static final String USER_CONFIG_FILE = "config.yaml";

    private final Path userConfigPath;

    public ConfigRepository() {
        this(defaultUserConfigPath());
    }

    public ConfigRepository(Path userConfigPath) {
        this.userConfigPath = Objects.requireNonNull(
            userConfigPath,
            "userConfigPath cannot be null"
        );
    }

    public AppConfig load() {
        if (!Files.isRegularFile(userConfigPath)) {
            return ConfigLoader.load();
        }

        try {
            return ConfigLoader.MAPPER.readValue(userConfigPath.toFile(), AppConfig.class);
        }
        catch (IOException | RuntimeException exception) {
            return ConfigLoader.load();
        }
    }

    private static Path defaultUserConfigPath() {
        return Path.of(
            System.getProperty("user.home"),
            USER_CONFIG_DIRECTORY,
            USER_CONFIG_FILE
        );
    }
}
