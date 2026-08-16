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
import com.cuseto.pong.view.PongRenderer;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

/** Owns the JavaFX and game-loop objects for one active gameplay session. */
public final class GameplayController {
    private final GameSession gameSession;
    private final PaddleInputState inputState;
    private final GameLoop gameLoop;
    private final Scene scene;

    public GameplayController(AppConfig appConfig) {
        Objects.requireNonNull(appConfig, "appConfig cannot be null");

        gameSession = new GameSession(appConfig);
        inputState = new PaddleInputState();

        Canvas canvas = new Canvas(
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight()
        );
        PongRenderer renderer = new PongRenderer();
        renderer.render(canvas, gameSession);

        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;");
        scene = new Scene(
            root,
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight()
        );
        configureInput();

        gameLoop = new GameLoop(
            gameSession,
            new PaddleGameUpdater(inputState)
                .andThen(new BallGameUpdater())
                .andThen(new ScoreGameUpdater()),
            currentState -> renderer.render(canvas, currentState)
        );
    }

    private void configureInput() {
        scene.setOnKeyPressed(event -> {
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

        scene.setOnKeyReleased(event -> {
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
        return scene;
    }

    public GameSession gameSession() {
        return gameSession;
    }

    private void clearInput() {
        inputState.setLeftDirection(PaddleDirection.NONE);
        inputState.setRightDirection(PaddleDirection.NONE);
    }
}
