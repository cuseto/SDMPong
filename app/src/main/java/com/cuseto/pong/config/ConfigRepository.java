package com.cuseto.pong.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import com.cuseto.pong.config.schema.AppConfig;

/** Loads and saves external config or fallbacks on bundled defaults */
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

    public void save(AppConfig appConfig) {
        Objects.requireNonNull(appConfig, "appConfig cannot be null");

        Path parent = parentDirectory();
        try {
            Files.createDirectories(parent);
            Path temporaryConfig = Files.createTempFile(parent, "config-", ".yaml");
            try {
                ConfigLoader.MAPPER.writeValue(temporaryConfig.toFile(), appConfig);
                replaceConfigFile(temporaryConfig);
            }
            finally {
                Files.deleteIfExists(temporaryConfig);
            }
        }
        catch (IOException exception) {
            throw new UncheckedIOException("Could not save user configuration", exception);
        }
    }

    private void replaceConfigFile(Path temporaryConfig) throws IOException {
        try {
            Files.move(
                temporaryConfig,
                userConfigPath,
                StandardCopyOption.ATOMIC_MOVE,
                StandardCopyOption.REPLACE_EXISTING
            );
        }
        catch (AtomicMoveNotSupportedException exception) {
            Files.move(temporaryConfig, userConfigPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private Path parentDirectory() {
        Path parent = userConfigPath.toAbsolutePath().getParent();
        if (parent == null) {
            throw new IllegalStateException("userConfigPath must have a parent directory");
        }
        return parent;
    }

    private static Path defaultUserConfigPath() {
        return Path.of(
            System.getProperty("user.home"),
            USER_CONFIG_DIRECTORY,
            USER_CONFIG_FILE
        );
    }
}
