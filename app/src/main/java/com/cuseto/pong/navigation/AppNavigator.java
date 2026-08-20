package com.cuseto.pong.navigation;

import java.util.Objects;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.controller.GameplayController;
import com.cuseto.pong.options.controller.OptionsController;
import com.cuseto.pong.view.MainMenuRenderer;

import javafx.stage.Stage;

/** Coordinates full-screen application navigation and the active gameplay lifecycle.
 * The only owner of Stage.setScene()
 */
public final class AppNavigator {
    private final Stage stage;
    private final AppConfig appConfig;
    private final MainMenuRenderer mainMenuRenderer;

    private ApplicationScreen currentScreen;
    private GameplayController gameplayController;
    private OptionsController optionsController;

    public AppNavigator(Stage stage, AppConfig appConfig) {
        this.stage = Objects.requireNonNull(stage, "stage cannot be null");
        this.appConfig = Objects.requireNonNull(appConfig, "appConfig cannot be null");
        mainMenuRenderer = new MainMenuRenderer();
    }

    public void start() {
        stage.setTitle("Pong");
        showMainMenu();
        stage.show();
    }

    public void showMainMenu() {
        stopGameplay();
        stage.setScene(mainMenuRenderer.createScene(
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight(),
            this::startGameplay,
            this::openOptionsMenu
        ));
        currentScreen = ApplicationScreen.MAIN_MENU;
    }

    public void startGameplay() {
        stopGameplay();
        gameplayController = new GameplayController(appConfig, this::showMainMenu);
        stage.setScene(gameplayController.scene());
        currentScreen = ApplicationScreen.GAMEPLAY;
        gameplayController.start();
    }

    public void openOptionsMenu() {
        currentScreen = ApplicationScreen.OPTIONS;
        optionsController = new OptionsController(appConfig, this::showMainMenu);
        stage.setScene(optionsController.scene());
    }

    public void stop() {
        stopGameplay();
    }

    public ApplicationScreen currentScreen() {
        return currentScreen;
    }

    public GameplayController gameplayController() {
        return gameplayController;
    }

    private void stopGameplay() {
        if (gameplayController != null) {
            gameplayController.stop();
            gameplayController = null;
        }
    }
}
