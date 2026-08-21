package com.cuseto.pong.game.controller;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.nio.file.Path;
import org.junit.jupiter.api.io.TempDir;


import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.ConfigRepository;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;
import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.Paddle;
import com.cuseto.pong.navigation.ApplicationScreen;
import com.cuseto.pong.navigation.AppNavigator;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

class GameplayControllerTest extends ApplicationTest {
    @TempDir
    Path temporaryDirectory;

    private AppConfig appConfig;
    private AppNavigator navigator;

    @Override
    public void start(Stage stage) {
        appConfig = ConfigLoader.load("/test-config.yaml");
        ConfigRepository configRepository = new ConfigRepository(temporaryDirectory.resolve("config.yaml"));

        configRepository.save(appConfig);

        navigator = new AppNavigator(stage, appConfig, configRepository);
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
        assertFalse(lookup("#pauseMenu").tryQuery().isPresent());
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#pauseMenu").tryQuery().isPresent());
    }

    @Test
    void menuClosesWhenEscIsPressed() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#pauseMenu").tryQuery().isPresent());
        pressKeyOnScene(KeyCode.ESCAPE);
        assertFalse(lookup("#pauseMenu").tryQuery().isPresent());
    }

    @Test
    void leftPaddleCantMoveWhenMenuIsOpen() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#pauseMenu").tryQuery().isPresent());

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
        assertTrue(lookup("#pauseMenu").tryQuery().isPresent());

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

    @Test
    void ballCantMoveWhenMenuIsOpen() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#pauseMenu").tryQuery().isPresent());

        Ball ball = navigator.gameplayController().gameSession().ball();
        double startingX = ball.x();
        double startingY = ball.y();

        sleep(100);

        assertTrue(startingX == ball.x());
        assertTrue(startingY == ball.y());
    }

    @Test
    void leftPaddleCanMoveWhenMenuIsClosed() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#pauseMenu").tryQuery().isPresent());
        pressKeyOnScene(KeyCode.ESCAPE);
        assertFalse(lookup("#pauseMenu").tryQuery().isPresent());

        Paddle leftPaddle = navigator.gameplayController().gameSession().leftPaddle();
        double startingY = leftPaddle.y();

        pressKeyOnScene(KeyCode.W);
        sleep(100);

        double endingY = leftPaddle.y();
        assertTrue(startingY > endingY);

        startingY = leftPaddle.y();
        pressKeyOnScene(KeyCode.S);
        sleep(100);

        endingY = leftPaddle.y();
        assertTrue(startingY < endingY);
    }

    @Test
    void rightPaddleCanMoveWhenMenuIsClosed() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#pauseMenu").tryQuery().isPresent());
        pressKeyOnScene(KeyCode.ESCAPE);
        assertFalse(lookup("#pauseMenu").tryQuery().isPresent());

        Paddle rightPaddle = navigator.gameplayController().gameSession().rightPaddle();
        double startingY = rightPaddle.y();

        pressKeyOnScene(KeyCode.UP);
        sleep(100);

        double endingY = rightPaddle.y();
        assertTrue(startingY > endingY);

        startingY = rightPaddle.y();
        pressKeyOnScene(KeyCode.DOWN);
        sleep(100);

        endingY = rightPaddle.y();
        assertTrue(startingY < endingY);
    }

    @Test
    void ballCanMoveWhenMenuIsClosed() {
        pressKeyOnScene(KeyCode.ESCAPE);
        assertTrue(lookup("#pauseMenu").tryQuery().isPresent());
        pressKeyOnScene(KeyCode.ESCAPE);
        assertFalse(lookup("#pauseMenu").tryQuery().isPresent());

        Ball ball = navigator.gameplayController().gameSession().ball();
        double startingX = ball.x();
        double startingY = ball.y();

        sleep(100);

        assertTrue(startingX != ball.x());
        assertTrue(startingY != ball.y());
    }

    @Test
    void winningBannerAppearsIfLeftPlayerWins() {
        GameSession gameSession =
            navigator.gameplayController().gameSession();

        interact(() -> {
            for (int i = 0; i < gameSession.winningScore(); i++) {
                gameSession.incrementLeftScore();
            }
        });

        assertTrue(
            lookup("#winnerBanner").tryQuery().isPresent()
        );
        assertTrue(gameSession.isGameOver());
        assertEquals(Player.LEFT, gameSession.winner());
        assertFalse(lookup("#pauseMenu").tryQuery().isPresent());
    }

    @Test
    void pauseMenuDoesNotAppearIfLeftPlayerWins() {
        GameSession gameSession =
            navigator.gameplayController().gameSession();

        assertFalse(lookup("#pauseMenu").tryQuery().isPresent());

        interact(() -> {
            for (int i = 0; i < gameSession.winningScore(); i++) {
                gameSession.incrementLeftScore();
            }
        });

        assertTrue(
            lookup("#winnerBanner").tryQuery().isPresent()
        );
        assertFalse(
            lookup("#pauseMenu").tryQuery().isPresent()
        );
    }

    @Test
    void newMatchStartsFromMatchFinishedMenu() {
        GameSession gameSession =
            navigator.gameplayController().gameSession();

        interact(() -> {
            for (int i = 0; i < gameSession.winningScore(); i++) {
                gameSession.incrementLeftScore();
            }
        });

        assertTrue(gameSession.isGameOver());
        assertTrue(lookup("#winnerBanner").tryQuery().isPresent());

        pressKeyOnScene(KeyCode.ENTER);

        assertTrue(gameSession.isGameOn());
        assertEquals(0, gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
        assertNull(gameSession.winner());
        assertFalse(lookup("#winnerBanner").tryQuery().isPresent());
    }

    @Test
    void escapeQuitsFromMatchFinishedMenu() {
        GameSession gameSession =
            navigator.gameplayController().gameSession();

        interact(() -> {
            for (int i = 0; i < gameSession.winningScore(); i++) {
                gameSession.incrementLeftScore();
            }
        });

        assertTrue(lookup("#winnerBanner").tryQuery().isPresent());

        pressKeyOnScene(KeyCode.ESCAPE);

        assertEquals(ApplicationScreen.MAIN_MENU, navigator.currentScreen());
        assertNull(navigator.gameplayController());
        assertTrue(lookup("#startGameButton").tryQuery().isPresent());
        assertFalse(lookup("#winnerBanner").tryQuery().isPresent());
    }

}
