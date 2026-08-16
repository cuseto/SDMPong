package com.cuseto.pong.navigation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.controller.GameplayController;
import com.cuseto.pong.game.session.GameSession;

import javafx.scene.control.Button;
import javafx.stage.Stage;

class AppNavigatorTest extends ApplicationTest {
    private AppConfig appConfig;
    private AppNavigator navigator;

    @Override
    public void start(Stage stage) {
        appConfig = ConfigLoader.load("/test-config.yaml");
        navigator = new AppNavigator(stage, appConfig);
        navigator.start();
    }

    @AfterEach
    void stopNavigator() {
        interact(navigator::stop);
    }

    @Test
    void applicationStartsAtMainMenu() {
        assertEquals(ApplicationScreen.MAIN_MENU, navigator.currentScreen());
        assertEquals(GameplayOverlay.NONE, navigator.gameplayOverlay());
        assertNotNull(lookup("#startGameButton").query());
    }

    @Test
    void startGameButtonNavigatesToGameplay() {
        startGameplayFromMenu();

        assertEquals(ApplicationScreen.GAMEPLAY, navigator.currentScreen());
        assertEquals(GameplayOverlay.NONE, navigator.gameplayOverlay());
        assertNotNull(navigator.gameplayController());
    }

    @Test
    void gameplayStartedFromMainMenuUsesConfiguredInitialState() {
        startGameplayFromMenu();
        GameplayController gameplayController = navigator.gameplayController();
        GameSession gameSession = gameplayController.gameSession();

        assertAll(
            () -> assertEquals(appConfig.gamePage().winningScore(), gameSession.winningScore()),
            () -> assertEquals(appConfig.gamePage().ball().radius(), gameSession.ball().radius()),
            () -> assertEquals(appConfig.gamePage().paddle().speed(), gameSession.leftPaddle().speed()),
            () -> assertEquals(appConfig.gamePage().paddle().speed(), gameSession.rightPaddle().speed()),
            () -> assertEquals(
                appConfig.viewport().screenWidth() - 2 * appConfig.gamePage().arena().spacingOther(),
                gameSession.arena().width()
            )
        );
    }

    @Test
    void returningToMainMenuDisposesTheActiveGameplayController() {
        startGameplayFromMenu();

        interact(navigator::showMainMenu);

        assertEquals(ApplicationScreen.MAIN_MENU, navigator.currentScreen());
        assertNull(navigator.gameplayController());
        assertNotNull(lookup("#startGameButton").query());
    }

    private void startGameplayFromMenu() {
        interact(() -> lookup("#startGameButton").queryAs(Button.class).fire());
    }
}
