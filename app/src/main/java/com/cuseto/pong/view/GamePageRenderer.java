package com.cuseto.pong.view;

import com.cuseto.pong.game.session.GameSession;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public final class GamePageRenderer {
    private final Scene scene;
    private final StackPane root;
    private final PongRenderer pongField;
    private final GameMenuRenderer gameMenu;

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
        gameMenu = new GameMenuRenderer();
        gameMenu.setOnCloseMenu(closeMenuFunction);
        gameMenu.setOnExitGame(exitGameFunction);
        root.getChildren().add(pongField.canvas());
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

    public Scene scene() {
        return scene;
    }
}