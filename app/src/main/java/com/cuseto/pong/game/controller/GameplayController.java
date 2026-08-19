package com.cuseto.pong.game.controller;

import java.util.Objects;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.input.PaddleInputState;
import com.cuseto.pong.game.input.PaddleKeyMapping;
import com.cuseto.pong.game.loop.GameLoop;
import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;
import com.cuseto.pong.game.update.BallGameUpdater;
import com.cuseto.pong.game.update.PaddleGameUpdater;
import com.cuseto.pong.game.update.ScoreGameUpdater;
import com.cuseto.pong.model.PaddleDirection;
import com.cuseto.pong.view.GamePageRenderer;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/** Owns the JavaFX and game-loop objects for one active gameplay session. */
public final class GameplayController {
    private final GameSession gameSession;
    private final PaddleInputState inputState;
    private final GameLoop gameLoop;
    private final GamePageRenderer gamePageRenderer;
    private final Runnable backToMainMenuFunction;
    private final KeyCode OPEN_MENU_KEY = KeyCode.ESCAPE;

    public GameplayController(AppConfig appConfig, Runnable backToMainMenuFunction) {
        Objects.requireNonNull(appConfig, "appConfig cannot be null");
        this.backToMainMenuFunction = Objects.requireNonNull(
            backToMainMenuFunction,
            "back to main menu function cannot be null"
        );

        gameSession = new GameSession(appConfig);
        gameSession.setGameOverAction(this::openWinnerBanner);
        inputState = new PaddleInputState();

        gamePageRenderer = new GamePageRenderer(
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight(),
            this::closeGameMenu,
            this.backToMainMenuFunction
        );
        gamePageRenderer.render(gameSession);
        configureInput();

        gameLoop = new GameLoop(
            gameSession,
            new PaddleGameUpdater(inputState)
                .andThen(new BallGameUpdater())
                .andThen(new ScoreGameUpdater()),
            currentState -> gamePageRenderer.render(currentState)
        );
    }

    private void configureInput() {
        gamePageRenderer.scene().setOnKeyPressed(event -> {
            if (gameSession.isGameOver()) {
                handleFinishedMatchKey(event.getCode());
                return;
            }

            if (event.getCode() == OPEN_MENU_KEY) {
                if (gameSession.isGameOn()) openGameMenu();
                else if (gameSession.isGamePaused()) closeGameMenu();
            }

            PaddleDirection leftDirection = PaddleKeyMapping.leftDirectionFor(event.getCode());
            if (leftDirection != PaddleDirection.NONE) {
                inputState.setLeftDirection(leftDirection);
            }
            PaddleDirection rightDirection = PaddleKeyMapping.rightDirectionFor(event.getCode());
            if (rightDirection != PaddleDirection.NONE) {
                inputState.setRightDirection(rightDirection);
            }
        });

        gamePageRenderer.scene().setOnKeyReleased(event -> {
            if (PaddleKeyMapping.leftDirectionFor(event.getCode()) != PaddleDirection.NONE) {
                inputState.setLeftDirection(PaddleDirection.NONE);
            }
            if (PaddleKeyMapping.rightDirectionFor(event.getCode()) != PaddleDirection.NONE) {
                inputState.setRightDirection(PaddleDirection.NONE);
            }
        });
    }

    private void handleFinishedMatchKey(KeyCode keyCode) {
        if (keyCode == KeyCode.ENTER) {
            gameSession.startNewMatch();
            gamePageRenderer.hideWinnerBanner();
            clearInput();
        }
        else if (keyCode == KeyCode.ESCAPE) {
            backToMainMenuFunction.run();
        }
    }

    private void openGameMenu() {
        gameSession.pauseGame();
        gamePageRenderer.showGameMenu();
    }

    private void closeGameMenu() {
        gamePageRenderer.hideGameMenu();
        gameSession.resumeGame();
    }

    private void openWinnerBanner(Player winner) {
        gamePageRenderer.showWinnerBanner(winner);
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
        clearInput();
    }

    public Scene scene() {
        return gamePageRenderer.scene();
    }

    public GameSession gameSession() {
        return gameSession;
    }

    private void clearInput() {
        inputState.setLeftDirection(PaddleDirection.NONE);
        inputState.setRightDirection(PaddleDirection.NONE);
    }
}
