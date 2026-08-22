package com.cuseto.pong.view;

import com.cuseto.pong.game.session.Player;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public final class GameWinnerBannerRenderer {
    private static final String WINNER_BANNER_ID = "winnerBanner";

    private final StackPane root;
    private final Label winnerLabel;

    public GameWinnerBannerRenderer() {
        winnerLabel = new Label();
        winnerLabel.setTextFill(Color.WHITE);
        winnerLabel.setFont(Font.font(30));

        Label instructionsLabel = new Label("ENTER: NEW MATCH    ESC: QUIT");
        instructionsLabel.setTextFill(Color.WHITE);
        instructionsLabel.setFont(Font.font(18));

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

    public void render(Player winner) {
        winnerLabel.setText(
            winner == Player.LEFT
                ? "LEFT PLAYER WINS!"
                : "RIGHT PLAYER WINS!"
        );
    }

    public StackPane root() {
        return root;
    }
}
