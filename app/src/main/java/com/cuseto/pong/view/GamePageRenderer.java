package com.cuseto.pong.view;

import com.cuseto.pong.game.session.GameSession;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public final class GamePageRenderer {
    private final Scene scene;
    private final StackPane root;
    private final PongRenderer pongField;

    public GamePageRenderer(double windowWidth, double windowHeight) {
        root = new StackPane();
        root.setStyle("-fx-background-color: black;");

        scene = new Scene(root, windowWidth, windowHeight);

        pongField = new PongRenderer(windowWidth, windowHeight);
        root.getChildren().add(pongField.canvas());
    }

    public void render(GameSession gameSession) {
        pongField.render(gameSession);
    }

    public Scene scene() {
        return scene;
    }
}