package com.cuseto.pong.game.controller;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.model.Paddle;
import com.cuseto.pong.navigation.AppNavigator;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

class GameControllerTest extends ApplicationTest {
    private AppConfig appConfig;
    private AppNavigator navigator; 

    @Override
    public void start(Stage stage) {
        appConfig = ConfigLoader.load("/test-config.yaml");
        navigator = new AppNavigator(stage, appConfig);
        navigator.start();
        navigator.startGameplay();
    }

    @AfterEach
    void stopNavigator() {
        interact(navigator::stop);
    }

    void pressKeyOnScene(KeyCode key) {
        interact(() -> navigator.gameplayController()
            .scene()
            .getRoot()
            .fireEvent(new KeyEvent(
                KeyEvent.KEY_PRESSED,
                "",
                "",
                key,
                false,
                false,
                false,
                false
            ))
        );
    }

    @Test
    void menuAppearsWhenEscIsPressed() {
        assertFalse(lookup("#gameMenu").tryQuery().isPresent());
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#gameMenu").tryQuery().isPresent());
    }

    @Test
    void menuClosesWhenEscIsPressed() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#gameMenu").tryQuery().isPresent());
        pressKeyOnScene(KeyCode.ESCAPE);
        assertFalse(lookup("#gameMenu").tryQuery().isPresent());
    }

    @Test
    void leftPaddleCantMoveWhenMenuIsOpen() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#gameMenu").tryQuery().isPresent());

        Paddle leftPaddle = navigator.gameplayController().gameSession().leftPaddle();
        double startingY = leftPaddle.y();

        pressKeyOnScene(KeyCode.W);

        double endingY = leftPaddle.y();
        assertTrue(startingY == endingY);

        startingY = leftPaddle.y();
        pressKeyOnScene(KeyCode.S);

        endingY = leftPaddle.y();
        assertTrue(startingY == endingY);
    }

    @Test
    void rightPaddleCantMoveWhenMenuIsOpen() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#gameMenu").tryQuery().isPresent());

        Paddle rightPaddle = navigator.gameplayController().gameSession().rightPaddle();
        double startingY = rightPaddle.y();

        pressKeyOnScene(KeyCode.UP);

        double endingY = rightPaddle.y();
        assertTrue(startingY == endingY);

        startingY = rightPaddle.y();
        pressKeyOnScene(KeyCode.DOWN);

        endingY = rightPaddle.y();
        assertTrue(startingY == endingY);
    }

}
