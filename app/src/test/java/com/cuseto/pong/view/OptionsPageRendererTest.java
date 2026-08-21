package com.cuseto.pong.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;

class OptionsPageRendererTest {
    @Test
    void optionsPageContainsEditableMatchSettingsControls() {
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsPageRenderer renderer = new OptionsPageRenderer(
            config.viewport().screenWidth(),
            config.viewport().screenHeight(),
            config
        );

        Scene scene = renderer.scene();

        VBox root = (VBox) scene.getRoot();

        TextField winningScoreField =
            (TextField) root.lookup("#optionsWinningScoreField");

        TextField ballSpeedField =
            (TextField) root.lookup("#optionsBallSpeedField");

        TextField paddleSpeedField =
            (TextField) root.lookup("#optionsPaddleSpeedField");

        assertNotNull(winningScoreField);
        assertNotNull(ballSpeedField);
        assertNotNull(paddleSpeedField);

        assertTrue(winningScoreField.isEditable());
        assertTrue(ballSpeedField.isEditable());
        assertTrue(paddleSpeedField.isEditable());
    }

    @Test
    void optionsPageDisplaysCurrentMatchSettings() {
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsPageRenderer renderer = new OptionsPageRenderer(
            config.viewport().screenWidth(),
            config.viewport().screenHeight(),
            config
        );

        Scene scene = renderer.scene();
        VBox root = (VBox) scene.getRoot();

        TextField winningScoreField =
            (TextField) root.lookup("#optionsWinningScoreField");

        TextField ballSpeedField =
            (TextField) root.lookup("#optionsBallSpeedField");

        TextField paddleSpeedField =
            (TextField) root.lookup("#optionsPaddleSpeedField");

        assertEquals(
            String.valueOf(config.gamePage().winningScore()),
            winningScoreField.getText()
        );

        assertEquals(
            String.valueOf(config.gamePage().ball().initialVelocityX()),
            ballSpeedField.getText()
        );

        assertEquals(
            String.valueOf(config.gamePage().paddle().speed()),
            paddleSpeedField.getText()
        );
    }

    @Test
    void optionsPageContainsSaveButton() {
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsPageRenderer renderer = new OptionsPageRenderer(
            config.viewport().screenWidth(),
            config.viewport().screenHeight(),
            config
        );

        Scene scene = renderer.scene();

        Button saveButton = (Button) scene.getRoot().lookup("#optionsSaveButton");

        assertNotNull(saveButton);
    }

    @Test
    void saveButtonInvokesSaveAction() {
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        OptionsPageRenderer renderer = new OptionsPageRenderer(
            config.viewport().screenWidth(),
            config.viewport().screenHeight(),
            config
        );

        boolean[] saveCalled = { false };

        renderer.setClickOnSaveButton(() -> saveCalled[0] = true);

        Button saveButton = (Button) renderer.scene().getRoot().lookup("#optionsSaveButton");
        saveButton.fire();

        assertTrue(saveCalled[0]);
    }
}
