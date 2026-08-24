package com.cuseto.pong.game.controller;

import java.util.Objects;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.input.PaddleInputState;
import com.cuseto.pong.game.input.PaddleKeyMapping;
import com.cuseto.pong.game.loop.GameLoop;
import com.cuseto.pong.game.model.PaddleDirection;
import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;
import com.cuseto.pong.game.update.BallGameUpdater;
import com.cuseto.pong.game.update.PaddleGameUpdater;
import com.cuseto.pong.game.update.ScoreGameUpdater;
import com.cuseto.pong.view.GamePageRenderer;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * Owns the JavaFX and game-loop objects for one active gameplay session.
 *
 * <p>On construction, this wires together a fresh {@link GameSession}, its
 * input state, key mapping, renderer, and {@link GameLoop} into a single
 * running match, and installs the key handlers that drive paddle input,
 * pause/resume, and returning to the main menu. Callers start and stop the
 * underlying loop via {@link #start()} and {@link #stop()}, and obtain the
 * gameplay {@link Scene} via {@link #scene()} to attach it to the window.
 */
public final class GameplayController {
    private final GameSession gameSession;
    private final PaddleInputState inputState;
    private final PaddleKeyMapping paddleKeyMapping;
    private final GameLoop gameLoop;
    private final GamePageRenderer gamePageRenderer;
    private final Runnable backToMainMenuFunction;
    private final KeyCode OPEN_MENU_KEY = KeyCode.ESCAPE;

    /**
     * Creates and wires up a new gameplay session for the given configuration,
     * rendering the initial frame and installing input handling, but does not
     * start the game loop; call {@link #start()} to begin play.
     *
     * @param appConfig the application configuration used to size the viewport, set up controls, and initialise the {@link GameSession}; must not be {@code null}
     * @param backToMainMenuFunction the callback invoked to leave gameplay and return to the main menu, e.g. when the player presses Escape from a finished match; must not be {@code null}
     * @throws NullPointerException if {@code appConfig} or {@code backToMainMenuFunction} is {@code null}
     */
    public GameplayController(AppConfig appConfig, Runnable backToMainMenuFunction) {
        Objects.requireNonNull(appConfig, "appConfig cannot be null");
        this.backToMainMenuFunction = Objects.requireNonNull(
            backToMainMenuFunction,
            "back to main menu function cannot be null"
        );

        gameSession = new GameSession(appConfig);
        gameSession.setGameOverAction(this::openWinnerBanner);
        inputState = new PaddleInputState();

        paddleKeyMapping = new PaddleKeyMapping(appConfig.controls());

        gamePageRenderer = new GamePageRenderer(
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight(),
            this::closePauseMenu,
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
                if (gameSession.isGameOn()) openPauseMenu();
                else if (gameSession.isGamePaused()) closePauseMenu();
            }

            PaddleDirection leftDirection = paddleKeyMapping.leftDirectionFor(event.getCode());
            if (leftDirection != PaddleDirection.NONE) {
                inputState.setLeftDirection(leftDirection);
            }
            PaddleDirection rightDirection = paddleKeyMapping.rightDirectionFor(event.getCode());
            if (rightDirection != PaddleDirection.NONE) {
                inputState.setRightDirection(rightDirection);
            }
        });

        gamePageRenderer.scene().setOnKeyReleased(event -> {
            if (paddleKeyMapping.leftDirectionFor(event.getCode()) != PaddleDirection.NONE) {
                inputState.setLeftDirection(PaddleDirection.NONE);
            }
            if (paddleKeyMapping.rightDirectionFor(event.getCode()) != PaddleDirection.NONE) {
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

    private void openPauseMenu() {
        gameSession.pauseGame();
        gamePageRenderer.showPauseMenu();
    }

    private void closePauseMenu() {
        gamePageRenderer.hidePauseMenu();
        gameSession.resumeGame();
    }

    private void openWinnerBanner(Player winner) {
        gamePageRenderer.showWinnerBanner(winner);
    }

    /**
     * Starts the game loop, beginning per-frame updates and rendering.
     */
    public void start() {
        gameLoop.start();
    }

    /**
     * Stops the game loop and clears any in-progress paddle input, so no
     * paddle is left mid-move if a key release is missed while stopped.
     */
    public void stop() {
        gameLoop.stop();
        clearInput();
    }

    /**
     * Returns the JavaFX scene for this gameplay session, for attaching to
     * the application window.
     *
     * @return the gameplay scene
     */
    public Scene scene() {
        return gamePageRenderer.scene();
    }

    /**
     * Returns the game session backing this controller.
     *
     * @return the current game session
     */
    public GameSession gameSession() {
        return gameSession;
    }

    private void clearInput() {
        inputState.setLeftDirection(PaddleDirection.NONE);
        inputState.setRightDirection(PaddleDirection.NONE);
    }
}
