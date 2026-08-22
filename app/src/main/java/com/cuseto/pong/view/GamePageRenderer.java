package com.cuseto.pong.view;

import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public final class GamePageRenderer {
    private final Scene scene;
    private final StackPane root;
    private final PongRenderer pongField;
    private final PauseMenuRenderer pauseMenu;
    private final GameWinnerBannerRenderer gameWinnerBanner;

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

    public void render(GameSession gameSession) {
        pongField.render(gameSession);
    }

    public void showPauseMenu() {
        if (!root.getChildren().contains(pauseMenu.root())) {
            root.getChildren().add(pauseMenu.root());
        }
    }

    public void hidePauseMenu() {
        if (root.getChildren().contains(pauseMenu.root())) {
            root.getChildren().remove(pauseMenu.root());
        }
    }

    public void showWinnerBanner(Player winner) {
        if (!root.getChildren().contains(gameWinnerBanner.root())) {
            gameWinnerBanner.render(winner);
            root.getChildren().add(gameWinnerBanner.root());
        }
    }

    public void hideWinnerBanner() {
        root.getChildren().remove(gameWinnerBanner.root());
    }

    public Scene scene() {
        return scene;
    }
}
