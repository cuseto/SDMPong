package com.cuseto.pong.view;

import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

/**
 * Composes the gameplay {@link Scene}: the {@link PongRenderer} canvas,
 * with the {@link PauseMenuRenderer} and {@link GameWinnerBannerRenderer}
 * overlaid on top when shown.
 *
 * <p>The pause menu and winner banner are added to and removed from the
 * scene graph on demand via {@link #showPauseMenu()}/{@link
 * #hidePauseMenu()} and {@link #showWinnerBanner(Player)}/{@link
 * #hideWinnerBanner()}; each show/hide method is safe to call even if the
 * overlay is already in the requested state.
 */
public final class GamePageRenderer {
    private final Scene scene;
    private final StackPane root;
    private final PongRenderer pongField;
    private final PauseMenuRenderer pauseMenu;
    private final GameWinnerBannerRenderer gameWinnerBanner;

    /**
     * Creates the gameplay scene at the given size, with the pause menu's
     * close and exit actions wired to the given callbacks.
     *
     * @param windowWidth the scene width, in pixels
     * @param windowHeight the scene height, in pixels
     * @param closeMenuFunction the callback invoked when the pause menu's close action is triggered
     * @param exitGameFunction the callback invoked when the pause menu's exit action is triggered
     */
    public GamePageRenderer(
        double windowWidth, 
        double windowHeight,
        Runnable closeMenuFunction,
        Runnable exitGameFunction
    ) {
        root = new StackPane();
        root.setStyle("-fx-background-color: black;");

        scene = new Scene(root, windowWidth, windowHeight);

        pongField = new PongRenderer(windowWidth, windowHeight);
        root.getChildren().add(pongField.canvas());

        pauseMenu = new PauseMenuRenderer();
        pauseMenu.setOnCloseMenu(closeMenuFunction);
        pauseMenu.setOnExitGame(exitGameFunction);

        gameWinnerBanner = new GameWinnerBannerRenderer();
    }

    /**
     * Redraws the gameplay canvas to reflect the given session's current
     * state. Does not affect the pause menu or winner banner overlays.
     *
     * @param gameSession the session to render; must not be {@code null}
     */
    public void render(GameSession gameSession) {
        pongField.render(gameSession);
    }

    /**
     * Shows the pause menu overlay, if it is not already shown.
     */
    public void showPauseMenu() {
        if (!root.getChildren().contains(pauseMenu.root())) {
            root.getChildren().add(pauseMenu.root());
        }
    }

    /**
     * Hides the pause menu overlay, if it is currently shown.
     */
    public void hidePauseMenu() {
        if (root.getChildren().contains(pauseMenu.root())) {
            root.getChildren().remove(pauseMenu.root());
        }
    }

    /**
     * Shows the winner banner overlay for the given winner, if it is not
     * already shown.
     *
     * @param winner the player to display as the winner; must not be {@code null}
     */
    public void showWinnerBanner(Player winner) {
        if (!root.getChildren().contains(gameWinnerBanner.root())) {
            gameWinnerBanner.render(winner);
            root.getChildren().add(gameWinnerBanner.root());
        }
    }

    /**
     * Hides the winner banner overlay. Does nothing if it is not currently shown.
     */
    public void hideWinnerBanner() {
        root.getChildren().remove(gameWinnerBanner.root());
    }

    /**
     * Returns the gameplay scene, for attaching to the application window.
     *
     * @return the gameplay scene
     */
    public Scene scene() {
        return scene;
    }
}
