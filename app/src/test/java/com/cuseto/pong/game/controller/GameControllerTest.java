package com.cuseto.pong.game.controller;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
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

    @Test
    void menuAppearsWhenEscIsPressed() {
        assertFalse(lookup("#gameMenu").tryQuery().isPresent());

        interact(() -> navigator.gameplayController()
            .scene()
            .getRoot()
            .fireEvent(new KeyEvent(
                KeyEvent.KEY_PRESSED,
                "",
                "",
                KeyCode.ESCAPE,
                false,
                false,
                false,
                false
            ))
        );

        assertTrue(lookup("#gameMenu").tryQuery().isPresent());
    }

}
