package com.cuseto.pong.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;

class OptionsPageRendererTest {
    private AppConfig config;
    private OptionsPageRenderer renderer;

    @BeforeEach
    void setUp() {
        config = ConfigLoader.load("/test-config.yaml");

        renderer = new OptionsPageRenderer(
            config.viewport().screenWidth(),
            config.viewport().screenHeight(),
            config
        );
    }
    
    @Test
    void optionsPageContainsEditableMatchSettingsControls() {
        Scene scene = renderer.scene();

        VBox root = (VBox) scene.getRoot();

        TextField winningScoreField = (TextField) root.lookup("#optionsWinningScoreField");

        TextField ballSpeedField = (TextField) root.lookup("#optionsBallSpeedField");

        TextField paddleSpeedField = (TextField) root.lookup("#optionsPaddleSpeedField");

        assertNotNull(winningScoreField);
        assertNotNull(ballSpeedField);
        assertNotNull(paddleSpeedField);

        assertTrue(winningScoreField.isEditable());
        assertTrue(ballSpeedField.isEditable());
        assertTrue(paddleSpeedField.isEditable());
    }

    @Test
    void optionsPageDisplaysCurrentMatchSettings() {
        Scene scene = renderer.scene();
        VBox root = (VBox) scene.getRoot();

        TextField winningScoreField = (TextField) root.lookup("#optionsWinningScoreField");

        TextField ballSpeedField = (TextField) root.lookup("#optionsBallSpeedField");

        TextField paddleSpeedField = (TextField) root.lookup("#optionsPaddleSpeedField");

        assertEquals(String.valueOf(config.gamePage().winningScore()), winningScoreField.getText());
        assertEquals(String.valueOf(config.gamePage().ball().initialVelocityX()), ballSpeedField.getText());
        assertEquals(String.valueOf(config.gamePage().paddle().speed()), paddleSpeedField.getText());
    }

    @Test
    void optionsPageContainsSaveButton() {
        Scene scene = renderer.scene();

        Button saveButton = (Button) scene.getRoot().lookup("#optionsSaveButton");

        assertNotNull(saveButton);
    }

    @Test
    void saveButtonInvokesSaveAction() {
        boolean[] saveCalled = { false };

        renderer.setClickOnSaveButton(() -> saveCalled[0] = true);

        Button saveButton = (Button) renderer.scene().getRoot().lookup("#optionsSaveButton");
        saveButton.fire();

        assertTrue(saveCalled[0]);
    }

    @Test
    void optionsPageContainsEditablePaddleControlButtons() {
        Scene scene = renderer.scene();
        VBox root = (VBox) scene.getRoot();

        Button leftPaddleUpButton = (Button) root.lookup("#optionsLeftPaddleUpButton");

        Button leftPaddleDownButton = (Button) root.lookup("#optionsLeftPaddleDownButton");

        Button rightPaddleUpButton = (Button) root.lookup("#optionsRightPaddleUpButton");

        Button rightPaddleDownButton = (Button) root.lookup("#optionsRightPaddleDownButton");

        assertNotNull(leftPaddleUpButton);
        assertNotNull(leftPaddleDownButton);
        assertNotNull(rightPaddleUpButton);
        assertNotNull(rightPaddleDownButton);
    }

    @Test
    void paddleControlButtonsDisplayConfiguredKeys() {
        Scene scene = renderer.scene();
        VBox root = (VBox) scene.getRoot();

        Button leftPaddleUpButton = (Button) root.lookup("#optionsLeftPaddleUpButton");

        Button leftPaddleDownButton = (Button) root.lookup("#optionsLeftPaddleDownButton");

        Button rightPaddleUpButton = (Button) root.lookup("#optionsRightPaddleUpButton");

        Button rightPaddleDownButton = (Button) root.lookup("#optionsRightPaddleDownButton");

        assertEquals(config.controls().leftPaddle().up().toString(), leftPaddleUpButton.getText());
        assertEquals(config.controls().leftPaddle().down().toString(), leftPaddleDownButton.getText());
        assertEquals(config.controls().rightPaddle().up().toString(), rightPaddleUpButton.getText());
        assertEquals(config.controls().rightPaddle().down().toString(), rightPaddleDownButton.getText());
    }

    @Test
    void pressingKeyAssignsItToSelectedPaddleControl() {
        Button button = (Button) renderer.scene().getRoot().lookup("#optionsLeftPaddleUpButton");

        button.fire();

        renderer.handleKeyPressed(
            new KeyEvent(
                KeyEvent.KEY_PRESSED,
                "",
                "",
                KeyCode.Q,
                false,
                false,
                false,
                false
            )
        );

        assertEquals("Q", button.getText());
    }

    @Test
    void pressingKeyAssignsItToSelectedRightPaddleControl() {
        Button button = (Button) renderer.scene().getRoot().lookup("#optionsRightPaddleDownButton");

        button.fire();

        renderer.handleKeyPressed(
            new KeyEvent(
                KeyEvent.KEY_PRESSED,
                "",
                "",
                KeyCode.X,
                false,
                false,
                false,
                false
            )
        );

        assertEquals("X", button.getText());
    }

    @Test
    void capturedPaddleControlsCanBeRead() {
        Button leftUpButton = (Button) renderer.scene().getRoot().lookup("#optionsLeftPaddleUpButton");

        Button leftDownButton = (Button) renderer.scene().getRoot().lookup("#optionsLeftPaddleDownButton");

        leftUpButton.fire();
        renderer.handleKeyPressed(keyPressed(KeyCode.Q));

        leftDownButton.fire();
        renderer.handleKeyPressed(keyPressed(KeyCode.X));

        assertEquals(KeyCode.Q, renderer.leftPaddleUpKey());
        assertEquals(KeyCode.X, renderer.leftPaddleDownKey());
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

    @Test
    void keyPressedOnSceneAssignsSelectedPaddleControl() {
        Button leftUpButton = (Button) renderer.scene().getRoot().lookup("#optionsLeftPaddleUpButton");

        leftUpButton.fire();

        renderer.scene().getRoot().fireEvent(keyPressed(KeyCode.Q));

        assertEquals(KeyCode.Q, renderer.leftPaddleUpKey());
        assertEquals("Q", leftUpButton.getText());
    }
}
