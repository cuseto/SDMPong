package com.cuseto.pong.view;

import com.cuseto.pong.game.session.Player;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Builds the winner banner overlay shown when a match finishes: which
 * player won, and the key bindings to start a new match or quit.
 *
 * <p>This class only builds the overlay's node tree and updates its text
 * via {@link #render(Player)}; it does not add or remove itself from a
 * scene. {@link GamePageRenderer} owns showing and hiding {@link #root()}
 * in the gameplay scene. The banner is mouse-transparent, so it does not
 * intercept clicks on the gameplay behind it.
 */
public final class GameWinnerBannerRenderer {
    private static final String WINNER_BANNER_ID = "winnerBanner";

    private final StackPane root;
    private final Label winnerLabel;

    /**
     * Builds the winner banner overlay's node tree, with no winner shown
     * until {@link #render(Player)} is called.
     */
    public GameWinnerBannerRenderer() {
        winnerLabel = new Label();
        winnerLabel.setTextFill(Color.WHITE);
        winnerLabel.setFont(Font.font("Monospaced", 30));

        Label instructionsLabel = new Label("ENTER: NEW MATCH    ESC: QUIT");
        instructionsLabel.setTextFill(Color.WHITE);
        instructionsLabel.setFont(Font.font("Monospaced", 18));

        VBox banner = new VBox(8, winnerLabel, instructionsLabel);
        banner.setAlignment(Pos.CENTER);
        banner.setMaxWidth(Double.MAX_VALUE);
        banner.setPrefHeight(96);
        banner.setMaxHeight(96);
        banner.setStyle("-fx-background-color: gray;");

        root = new StackPane(banner);
        root.setId(WINNER_BANNER_ID);
        root.setMouseTransparent(true);
    }

    /**
     * Updates the banner text to announce the given player as the winner.
     *
     * @param winner the winning player; must not be {@code null}
     */
    public void render(Player winner) {
        winnerLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 30));
        winnerLabel.setText(
            winner == Player.LEFT
                ? "PLAYER 1 WINS!"
                : "PLAYER 2 WINS!"
        );
    }

    /**
     * Returns the root node of the winner banner overlay, for adding to
     * or removing from a scene graph.
     *
     * @return the overlay's root node
     */
    public StackPane root() {
        return root;
    }
}
