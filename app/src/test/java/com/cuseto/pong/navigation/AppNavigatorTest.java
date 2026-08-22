package com.cuseto.pong.navigation;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.nio.file.Path;

import org.junit.jupiter.api.io.TempDir;


import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.ConfigRepository;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.controller.GameplayController;
import com.cuseto.pong.game.session.GameSession;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

class AppNavigatorTest extends ApplicationTest {
    @TempDir
    Path temporaryDirectory;

    private AppConfig appConfig;
    private AppNavigator navigator;
    private ConfigRepository configRepository;

    @Override
    public void start(Stage stage) {
        appConfig = ConfigLoader.load("/test-config.yaml");
        configRepository = new ConfigRepository(temporaryDirectory.resolve("config.yaml"));

        configRepository.save(appConfig);

        navigator = new AppNavigator(
            stage,
            appConfig,
            configRepository
        );
        navigator.start();
    }

    @AfterEach
    void stopNavigator() {
        interact(navigator::stop);
    }

    @Test
    void applicationStartsAtMainMenu() {
        assertEquals(ApplicationScreen.MAIN_MENU, navigator.currentScreen());
        assertNotNull(lookup("#startGameButton").query());
    }

    @Test
    void startGameButtonNavigatesToGameplay() {
        startGameplayFromMenu();

        assertEquals(ApplicationScreen.GAMEPLAY, navigator.currentScreen());
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

    @Test
    void openOptionsPageFromMainMenu() {
        startGameplayFromMenu();
        interact(navigator::showMainMenu);

        assertFalse(lookup("#optionsPage").tryQuery().isPresent());
        interact(() -> lookup("#optionsPageButton").queryAs(Button.class).fire());
        assertTrue(lookup("#optionsPage").tryQuery().isPresent());
    }

    @Test
    void backButtonNavigatesFromOptionsPageToMainMenu() {
        assertFalse(lookup("#optionsPage").tryQuery().isPresent());
        interact(() -> lookup("#optionsPageButton").queryAs(Button.class).fire());
        assertTrue(lookup("#optionsPage").tryQuery().isPresent());

        interact(() -> lookup("#optionsBackButton").queryAs(Button.class).fire());

        assertFalse(lookup("#optionsPage").tryQuery().isPresent());
        assertEquals(ApplicationScreen.MAIN_MENU, navigator.currentScreen());
        assertNotNull(lookup("#startGameButton").query());
    }

    @Test
    void newMatchUsesSettingsSavedFromOptions() {
        interact(() -> lookup("#optionsPageButton").queryAs(Button.class).fire());

        assertTrue(lookup("#optionsPage").tryQuery().isPresent());

        interact(() -> {
            lookup("#optionsWinningScoreField").queryAs(TextField.class).setText("10");
            lookup("#optionsBallSpeedField").queryAs(TextField.class).setText("600.0");
            lookup("#optionsPaddleSpeedField").queryAs(TextField.class).setText("500.0");
        });

        interact(() -> lookup("#optionsSaveButton").queryAs(Button.class).fire());

        interact(() -> lookup("#optionsBackButton").queryAs(Button.class).fire());

        assertEquals(ApplicationScreen.MAIN_MENU,navigator.currentScreen());

        startGameplayFromMenu();

        GameSession gameSession = navigator.gameplayController().gameSession();

        assertAll(
            () -> assertEquals(10, gameSession.winningScore()),
            () -> assertEquals(600.0, gameSession.ball().velocityX()),
            () -> assertEquals(600.0, gameSession.ball().velocityY()),
            () -> assertEquals(500.0, gameSession.leftPaddle().speed()),
            () -> assertEquals(500.0, gameSession.rightPaddle().speed())
        );
    }

    @Test
    void savingOptionsDoesNotMutateActiveMatch() {
        startGameplayFromMenu();

        GameSession activeMatch = navigator.gameplayController().gameSession();

        int originalWinningScore = activeMatch.winningScore();
        double originalBallVelocityX = activeMatch.ball().velocityX();
        double originalBallVelocityY = activeMatch.ball().velocityY();
        double originalPaddleSpeed = activeMatch.leftPaddle().speed();

        interact(navigator::showMainMenu);

        interact(() -> lookup("#optionsPageButton").queryAs(Button.class).fire());

        interact(() -> {
            lookup("#optionsWinningScoreField").queryAs(TextField.class).setText("10");
            lookup("#optionsBallSpeedField").queryAs(TextField.class).setText("600.0");
            lookup("#optionsPaddleSpeedField").queryAs(TextField.class).setText("500.0");
        });

        interact(() -> lookup("#optionsSaveButton").queryAs(Button.class).fire());

        assertAll(
            () -> assertEquals(originalWinningScore, activeMatch.winningScore()),
            () -> assertEquals(originalBallVelocityX, activeMatch.ball().velocityX()),
            () -> assertEquals(originalBallVelocityY, activeMatch.ball().velocityY()),
            () -> assertEquals(originalPaddleSpeed, activeMatch.leftPaddle().speed())
        );
    }
}
