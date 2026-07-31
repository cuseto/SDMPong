package com.cuseto.pong.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.model.AppConfig;

import javafx.scene.input.KeyCode;

class ConfigLoaderTest {
    @Test
    void loadsApplicationConfigurationFromYamlResource() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");

        assertEquals(1024, appConfig.viewport().screenWidth());
        assertEquals(768, appConfig.viewport().screenHeight());

        assertEquals(50, appConfig.gamePage().arena().spacingTop());
        assertEquals(10, appConfig.gamePage().arena().spacingOther());
        assertEquals(3, appConfig.gamePage().arena().boundaryThickness());

        assertEquals(6, appConfig.gamePage().ball().radius());
        assertEquals(120.0, appConfig.gamePage().ball().initialVelocityX());
        assertEquals(80.0, appConfig.gamePage().ball().initialVelocityY());

        assertEquals(12, appConfig.gamePage().paddle().width());
        assertEquals(90, appConfig.gamePage().paddle().height());
        assertEquals(70, appConfig.gamePage().paddle().inset());
        assertEquals(300.0, appConfig.gamePage().paddle().speed());

        assertEquals(KeyCode.A, appConfig.controls().leftPaddle().up());
        assertEquals(KeyCode.Z, appConfig.controls().leftPaddle().down());
        assertEquals(KeyCode.UP, appConfig.controls().rightPaddle().up());
        assertEquals(KeyCode.DOWN, appConfig.controls().rightPaddle().down());
    }

    @Test
    void bundledDefaultConfigurationCanBeLoaded() {
        assertDoesNotThrow(() -> {
            ConfigLoader.load();
        });
    }
}
