package com.cuseto.pong.navigation;

import java.util.Objects;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.controller.GameplayController;

import javafx.stage.Stage;

/** Coordinates full-screen application navigation and the active gameplay lifecycle.
 * The only owner of Stage.setScene()
 */
public final class AppNavigator {
    private final Stage stage;
    private final AppConfig appConfig;

    private ApplicationScreen currentScreen;
    private GameplayOverlay gameplayOverlay = GameplayOverlay.NONE;
    private GameplayController gameplayController;

    public AppNavigator(Stage stage, AppConfig appConfig) {
        this.stage = Objects.requireNonNull(stage, "stage cannot be null");
        this.appConfig = Objects.requireNonNull(appConfig, "appConfig cannot be null");
    }

    public void start() {
        stage.setTitle("Pong");
        stage.show();
    }

    public void startGameplay() {
    }

    public void stop() {
        stopGameplay();
    }

    public ApplicationScreen currentScreen() {
        return currentScreen;
    }

    public GameplayOverlay gameplayOverlay() {
        return gameplayOverlay;
    }

    public GameplayController gameplayController() {
        return gameplayController;
    }

    private void stopGameplay() {
    
    }
}
