package com.cuseto.pong.view;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.cuseto.pong.view.components.DefaultButton;

public final class PauseMenuRenderer {
    private static final String MENU_ID = "pauseMenu";
    private static final String CLOSE_MENU_BUTTON_ID = "closePauseMenuButton";
    private static final String EXIT_GAME_BUTTON_ID = "exitGameButton";

    private final StackPane root;
    private final Button closeMenuButton;
    private final Button exitGameButton;

    public PauseMenuRenderer() {
        Label title = new Label("Match Paused");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("System", FontWeight.BOLD, 32));

        closeMenuButton = createButton("Resume", CLOSE_MENU_BUTTON_ID);
        exitGameButton = createButton("Quit Match", EXIT_GAME_BUTTON_ID);

        VBox menuPanel = new VBox(24, title, closeMenuButton, exitGameButton);
        menuPanel.setAlignment(Pos.CENTER);
        menuPanel.setPadding(new Insets(32));
        menuPanel.setMaxSize(380, 280);
        menuPanel.setStyle(
            "-fx-background-color: rgba(20, 20, 20, 0.95);" +
            "-fx-border-color: white;" +
            "-fx-border-width: 2;"
        );

        root = new StackPane(menuPanel);
        root.setId(MENU_ID);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.55);");
    }

    public void setOnCloseMenu(Runnable action) {
        Objects.requireNonNull(action, "close menu action cannot be null");
        closeMenuButton.setOnAction(event -> action.run());
    }

    public void setOnExitGame(Runnable action) {
        Objects.requireNonNull(action, "exit game action cannot be null");
        exitGameButton.setOnAction(event -> action.run());
    }

    public StackPane root() {
        return root;
    }

    private Button createButton(String text, String id) {
        Button button = new DefaultButton(text);
        button.setId(id);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(44);
        return button;
    }
}
