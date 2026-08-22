package com.cuseto.pong.options.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.ConfigRepository;
import com.cuseto.pong.config.schema.AppConfig;

class OptionsControllerTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void savePersistsValidMatchSettings() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        controller.saveMatchSettings("10", "500.0", "400.0");

        AppConfig savedConfig = repository.load();

        assertEquals(
            10,
            savedConfig.gamePage().winningScore()
        );
        assertEquals(
            500.0,
            savedConfig.gamePage().ball().initialVelocityX()
        );
        assertEquals(
            500.0,
            savedConfig.gamePage().ball().initialVelocityY()
        );
        assertEquals(
            400.0,
            savedConfig.gamePage().paddle().speed()
        );
    }

    @Test
    void invalidMatchSettingsAreNotSavedAndDisplayValidationFeedback() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        controller.saveMatchSettings("invalid", "500.0", "400.0");

        Label validationFeedback = (Label) controller.scene().getRoot().lookup("#optionsValidationFeedback");

        assertTrue(validationFeedback.isVisible());
        assertFalse(validationFeedback.getText().isBlank());
        assertFalse(Files.exists(userConfigPath));
    }

    @Test
    void invalidWinningScoreIsNotSaved() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        controller.saveMatchSettings("0", "500.0", "400.0");

        Label validationFeedback =
            (Label) controller.scene().getRoot().lookup("#optionsValidationFeedback");

        assertTrue(validationFeedback.isVisible());
        assertFalse(validationFeedback.getText().isBlank());
        assertFalse(Files.exists(userConfigPath));
    }

    @Test
    void negativeWinningScoreIsNotSaved() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        controller.saveMatchSettings("-1", "500.0", "400.0");

        Label validationFeedback =
            (Label) controller.scene().getRoot().lookup("#optionsValidationFeedback");

        assertTrue(validationFeedback.isVisible());
        assertFalse(validationFeedback.getText().isBlank());
        assertFalse(Files.exists(userConfigPath));
    }

    @Test
    void invalidBallSpeedIsNotSaved() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        controller.saveMatchSettings("10", "0.0", "400.0");

        Label validationFeedback =
            (Label) controller.scene().getRoot().lookup("#optionsValidationFeedback");

        assertTrue(validationFeedback.isVisible());
        assertFalse(validationFeedback.getText().isBlank());
        assertFalse(Files.exists(userConfigPath));
    }

    @Test
    void nonFiniteBallSpeedIsNotSaved() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        controller.saveMatchSettings("10", "NaN", "400.0");

        Label validationFeedback =
            (Label) controller.scene().getRoot().lookup("#optionsValidationFeedback");

        assertTrue(validationFeedback.isVisible());
        assertFalse(validationFeedback.getText().isBlank());
        assertFalse(Files.exists(userConfigPath));
    }

    @Test
    void invalidPaddleSpeedIsNotSaved() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        controller.saveMatchSettings("10", "500.0", "-1.0");

        Label validationFeedback =
            (Label) controller.scene().getRoot().lookup("#optionsValidationFeedback");

        assertTrue(validationFeedback.isVisible());
        assertFalse(validationFeedback.getText().isBlank());
        assertFalse(Files.exists(userConfigPath));
    }

    @Test
    void nonFinitePaddleSpeedIsNotSaved() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        controller.saveMatchSettings("10", "500.0", "Infinity");

        Label validationFeedback =
            (Label) controller.scene().getRoot().lookup("#optionsValidationFeedback");

        assertTrue(validationFeedback.isVisible());
        assertFalse(validationFeedback.getText().isBlank());
        assertFalse(Files.exists(userConfigPath));
    }

    @Test
    void savePersistsValidPaddleControlBindings() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsController controller = new OptionsController(
            config,
            repository,
            () -> {}
        );

        ((Button) controller.scene().getRoot().lookup("#optionsLeftPaddleUpButton")).fire();
        controller.scene().getRoot().fireEvent(keyPressed(KeyCode.Q));

        ((Button) controller.scene().getRoot().lookup("#optionsLeftPaddleDownButton")).fire();
        controller.scene().getRoot().fireEvent(keyPressed(KeyCode.X));

        ((Button) controller.scene().getRoot().lookup("#optionsRightPaddleUpButton")).fire();
        controller.scene().getRoot().fireEvent(keyPressed(KeyCode.I));

        ((Button) controller.scene().getRoot().lookup("#optionsRightPaddleDownButton")).fire();
        controller.scene().getRoot().fireEvent(keyPressed(KeyCode.K));

        controller.saveMatchSettings("10", "500.0", "400.0");

        AppConfig savedConfig = repository.load();

        assertEquals(KeyCode.Q, savedConfig.controls().leftPaddle().up());
        assertEquals(KeyCode.X, savedConfig.controls().leftPaddle().down());
        assertEquals(KeyCode.I, savedConfig.controls().rightPaddle().up());
        assertEquals(KeyCode.K, savedConfig.controls().rightPaddle().down());
    }

    private KeyEvent keyPressed(KeyCode keyCode) {
        return new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "",
            "",
            keyCode,
            false,
            false,
            false,
            false
        );
    }
}
