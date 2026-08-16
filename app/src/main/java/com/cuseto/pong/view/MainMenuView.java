package com.cuseto.pong.view;

import java.util.Objects;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/** Creates the application's main-menu scene. */
public final class MainMenuView {
    private static final String START_GAME_BUTTON_ID = "startGameButton";

    public Scene createScene(double width, double height, Runnable startGameAction) {
        Objects.requireNonNull(startGameAction, "startGameAction cannot be null");

        Label title = new Label("PONG");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font(48));

        Button startGameButton = new Button("Start Game");
        startGameButton.setId(START_GAME_BUTTON_ID);
        startGameButton.setOnAction(event -> startGameAction.run());

        VBox root = new VBox(24, title, startGameButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");

        return new Scene(root, width, height);
    }
}
