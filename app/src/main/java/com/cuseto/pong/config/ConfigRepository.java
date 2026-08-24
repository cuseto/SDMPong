package com.cuseto.pong.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import com.cuseto.pong.config.schema.AppConfig;

/**
 * Loads and saves user configuration on the filesystem, falling back to
 * {@link ConfigLoader}'s bundled defaults when no user configuration
 * exists or it cannot be read.
 *
 * <p>Saves are written atomically: {@link #save(AppConfig)} writes to a
 * temporary file in the same directory as the target, then moves it into
 * place, so a save that is interrupted or fails part-way through cannot
 * leave a corrupt or partially-written configuration file behind.
 */
public final class ConfigRepository {
    private static final String USER_CONFIG_DIRECTORY = ".sdm-pong";
    private static final String USER_CONFIG_FILE = "config.yaml";

    private final Path userConfigPath;

    /**
     * Creates a repository backed by the default per-user configuration
     * file, at {@code <user home>/.sdm-pong/config.yaml}.
     */
    public ConfigRepository() {
        this(defaultUserConfigPath());
    }

    /**
     * Creates a repository backed by the given configuration file path.
     *
     * @param userConfigPath the file to load configuration from and save configuration to; must not be {@code null}
     * @throws NullPointerException if {@code userConfigPath} is {@code null}
     */
    public ConfigRepository(Path userConfigPath) {
        this.userConfigPath = Objects.requireNonNull(
            userConfigPath,
            "userConfigPath cannot be null"
        );
    }

    /**
     * Loads the application configuration, preferring the user's saved
     * configuration file if one exists and can be read.
     *
     * @return the user's saved configuration, or {@link ConfigLoader}'s
     *         bundled default if no user configuration file exists, or it
     *         cannot be read or parsed
     */
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

    /**
     * Saves the given configuration to the user's configuration file,
     * atomically replacing any existing file.
     *
     * @param appConfig the configuration to save; must not be {@code null}
     * @throws NullPointerException if {@code appConfig} is {@code null}
     * @throws UncheckedIOException if the configuration cannot be written to disk
     */
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
