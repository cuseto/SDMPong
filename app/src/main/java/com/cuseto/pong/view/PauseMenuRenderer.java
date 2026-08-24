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

/**
 * Builds the pause menu overlay: a "Match Paused" panel with resume and
 * quit buttons, dimmed over the gameplay behind it.
 *
 * <p>This class only builds the overlay's node tree and lets callers wire
 * up its buttons; it does not add or remove itself from a scene. {@link
 * GamePageRenderer} owns showing and hiding {@link #root()} in the
 * gameplay scene.
 */
public final class PauseMenuRenderer {
    private static final String MENU_ID = "pauseMenu";
    private static final String CLOSE_MENU_BUTTON_ID = "closePauseMenuButton";
    private static final String EXIT_GAME_BUTTON_ID = "exitGameButton";

    private final StackPane root;
    private final Button closeMenuButton;
    private final Button exitGameButton;

    /**
     * Builds the pause menu overlay's node tree. The resume and quit
     * buttons have no action until {@link #setOnCloseMenu(Runnable)} and
     * {@link #setOnExitGame(Runnable)} are called.
     */
    public PauseMenuRenderer() {
        Label title = new Label("Match Paused");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Monospaced", FontWeight.BOLD, 32));

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

    /**
     * Sets the action run when the resume button is clicked.
     *
     * @param action the callback to run; must not be {@code null}
     * @throws NullPointerException if {@code action} is {@code null}
     */
    public void setOnCloseMenu(Runnable action) {
        Objects.requireNonNull(action, "close menu action cannot be null");
        closeMenuButton.setOnAction(event -> action.run());
    }

    /**
     * Sets the action run when the quit match button is clicked.
     *
     * @param action the callback to run; must not be {@code null}
     * @throws NullPointerException if {@code action} is {@code null}
     */
    public void setOnExitGame(Runnable action) {
        Objects.requireNonNull(action, "exit game action cannot be null");
        exitGameButton.setOnAction(event -> action.run());
    }

    /**
     * Returns the root node of the pause menu overlay, for adding to or
     * removing from a scene graph.
     *
     * @return the overlay's root node
     */
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
