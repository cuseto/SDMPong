package com.cuseto.pong.game.controller;

import java.util.Objects;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.input.PaddleInputState;
import com.cuseto.pong.game.input.PaddleKeyMapping;
import com.cuseto.pong.game.loop.GameLoop;
import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.update.BallGameUpdater;
import com.cuseto.pong.game.update.PaddleGameUpdater;
import com.cuseto.pong.game.update.ScoreGameUpdater;
import com.cuseto.pong.model.PaddleDirection;
import com.cuseto.pong.view.GamePageRenderer;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/** Owns the JavaFX and game-loop objects for one active gameplay session. */
public final class GameplayController {
    private final GameSession gameSession;
    private final PaddleInputState inputState;
    private final GameLoop gameLoop;
    private final GamePageRenderer gamePageRenderer;

    public GameplayController(AppConfig appConfig) {
        Objects.requireNonNull(appConfig, "appConfig cannot be null");

        gameSession = new GameSession(appConfig);
        inputState = new PaddleInputState();

        gamePageRenderer = new GamePageRenderer(
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight()
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
            if (gameSession.isMatchOver()) {
                handleFinishedMatchKey(event.getCode());
                return;
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
            clearInput();
        }
        else if (keyCode == KeyCode.ESCAPE) {
            Platform.exit();
        }
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
