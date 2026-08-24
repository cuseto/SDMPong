package com.cuseto.pong.view;

import com.cuseto.pong.view.components.DefaultButton;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Creates the application's main-menu scene. */
public final class MainMenuRenderer {
    private static final String START_GAME_BUTTON_ID = "startGameButton";
    private static final String OPTIONS_PAGE_BUTTON_ID = "optionsPageButton";

    private final Scene scene;
    private final VBox root;
    private final Button startGameButton;
    private final Button optionsButton;

    public MainMenuRenderer(
        double windowWidth,
        double windowHeight
    ) {
        Label title = new Label("|° Pong  |");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Monospaced", FontWeight.BOLD, 96));

        startGameButton = new DefaultButton("Start Game");
        startGameButton.setId(START_GAME_BUTTON_ID);

        optionsButton = new DefaultButton("Options");
        optionsButton.setId(OPTIONS_PAGE_BUTTON_ID);

        root = new VBox(24, title, startGameButton, optionsButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");

        scene = new Scene(root, windowWidth, windowHeight);
    }

    public void setClickOnStartGameButton(Runnable startGameAction) {
        startGameButton.setOnAction(event -> startGameAction.run());
    }

    public void setClickOnOptionsButton(Runnable openOptionsAction) {
        optionsButton.setOnAction(event -> openOptionsAction.run());
    }

    public Scene scene() {
        return scene;
    }
}
