package com.cuseto.pong.view;

import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public final class GamePageRenderer {
    private final Scene scene;
    private final StackPane root;
    private final PongRenderer pongField;
    private final GameMenuRenderer gameMenu;
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

        gameMenu = new GameMenuRenderer();
        gameMenu.setOnCloseMenu(closeMenuFunction);
        gameMenu.setOnExitGame(exitGameFunction);

        gameWinnerBanner = new GameWinnerBannerRenderer();
    }

    public void render(GameSession gameSession) {
        pongField.render(gameSession);
    }

    public void showGameMenu() {
        if (!root.getChildren().contains(gameMenu.root())) {
            root.getChildren().add(gameMenu.root());
        }
    }

    public void hideGameMenu() {
        if (root.getChildren().contains(gameMenu.root())) {
            root.getChildren().remove(gameMenu.root());
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
