package com.cuseto.pong.navigation;

import java.util.Objects;

import com.cuseto.pong.config.ConfigRepository;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.controller.GameplayController;
import com.cuseto.pong.options.controller.OptionsController;
import com.cuseto.pong.view.MainMenuRenderer;

import javafx.stage.Stage;

/**
 * Coordinates full-screen application navigation and the active gameplay
 * lifecycle.
 *
 * <p>This is the sole owner of {@link Stage#setScene}: switching between
 * the main menu, options menu, and gameplay always goes through one of
 * {@link #showMainMenu()}, {@link #openOptionsMenu()}, or
 * {@link #startGameplay()}, which each replace the stage's scene and
 * update {@link #currentScreen()} accordingly. Leaving gameplay always
 * stops the active {@link GameplayController} first, so at most one
 * gameplay session is ever running.
 */
public final class AppNavigator {
    private final Stage stage;
    private final MainMenuRenderer mainMenuRenderer;
    private final ConfigRepository configRepository;

    private AppConfig appConfig;
    private ApplicationScreen currentScreen;
    private GameplayController gameplayController;
    private OptionsController optionsController;

    /**
     * Creates a navigator for the given stage, wiring the main menu's
     * start-game and options buttons to this navigator's screen transitions.
     *
     * @param stage the JavaFX stage this navigator controls; must not be {@code null}
     * @param appConfig the initial application configuration, used to size the main menu and seed gameplay/options until the next {@link #showMainMenu()} reload; must not be {@code null}
     * @param configRepository the repository used to reload configuration when returning to the main menu; must not be {@code null}
     * @throws NullPointerException if any argument is {@code null}
     */
    public AppNavigator(Stage stage, AppConfig appConfig, ConfigRepository configRepository) {
        this.stage = Objects.requireNonNull(stage, "stage cannot be null");
        this.appConfig = Objects.requireNonNull(appConfig, "appConfig cannot be null");
        this.configRepository = Objects.requireNonNull(configRepository, "configRepository cannot be null");
        mainMenuRenderer = new MainMenuRenderer(
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight()
        );
        mainMenuRenderer.setClickOnStartGameButton(this::startGameplay);
        mainMenuRenderer.setClickOnOptionsButton(this::openOptionsMenu);
    }

    /**
     * Sets the stage title, shows the main menu, and makes the stage
     * visible. Intended to be called once, on application startup.
     */
    public void start() {
        stage.setTitle("Pong");
        showMainMenu();
        stage.show();
    }

    /**
     * Stops any active gameplay session, reloads configuration from
     * {@link ConfigRepository}, and switches the stage to the main menu.
     *
     * <p>Configuration is reloaded on every call so that changes saved
     * from the options menu take effect the next time the player starts
     * a game.
     */
    public void showMainMenu() {
        stopGameplay();
        appConfig = configRepository.load();
        stage.setScene(mainMenuRenderer.scene());
        currentScreen = ApplicationScreen.MAIN_MENU;
    }

    /**
     * Stops any active gameplay session, starts a new one using the
     * current configuration, and switches the stage to it.
     */
    public void startGameplay() {
        stopGameplay();
        gameplayController = new GameplayController(appConfig, this::showMainMenu);
        stage.setScene(gameplayController.scene());
        currentScreen = ApplicationScreen.GAMEPLAY;
        gameplayController.start();
    }

    /**
     * Opens the options menu, switching the stage to it.
     */
    public void openOptionsMenu() {
        currentScreen = ApplicationScreen.OPTIONS;
        optionsController = new OptionsController(
            appConfig,
            configRepository,
            this::showMainMenu
        );
        stage.setScene(optionsController.scene());
    }

    /**
     * Stops any active gameplay session. Intended to be called on
     * application shutdown.
     */
    public void stop() {
        stopGameplay();
    }

    /**
     * Returns the screen currently displayed on the stage.
     *
     * @return the current screen
     */
    public ApplicationScreen currentScreen() {
        return currentScreen;
    }

    /**
     * Returns the controller for the active gameplay session.
     *
     * @return the active gameplay controller, or {@code null} if no gameplay session is currently running
     */
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
