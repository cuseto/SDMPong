package com.cuseto.pong.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.cuseto.pong.config.schema.AppConfig;

class ConfigRepositoryTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void loadUsesBundledDefaultsWhenUserConfigurationDoesNotExist() {
        ConfigRepository repository = new ConfigRepository(userConfigPath());

        AppConfig loadedConfig = repository.load();

        assertEquals(ConfigLoader.load(), loadedConfig);
    }

    private Path userConfigPath() {
        return temporaryDirectory.resolve("config.yaml");
    }
}
