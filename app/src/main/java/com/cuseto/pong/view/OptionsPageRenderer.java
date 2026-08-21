package com.cuseto.pong.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import com.cuseto.pong.config.schema.AppConfig;

public final class OptionsPageRenderer {
    private static final String OPTIONS_PAGE_ID = "optionsPage";
    private static final String BACK_BUTTON_ID = "optionsBackButton";
    private static final String WINNING_SCORE_FIELD_ID = "optionsWinningScoreField";
    private static final String BALL_SPEED_FIELD_ID = "optionsBallSpeedField";
    private static final String PADDLE_SPEED_FIELD_ID = "optionsPaddleSpeedField";
    private static final String SAVE_BUTTON_ID = "optionsSaveButton";
    private static final String VALIDATION_FEEDBACK_ID = "optionsValidationFeedback";

    private final Scene scene;
    private final VBox root;
    private final Button backButton;
    private final TextField winningScoreField;
    private final TextField ballSpeedField;
    private final TextField paddleSpeedField;
    private final Button saveButton;
    private final Label validationFeedback;

    public OptionsPageRenderer(
        double windowWidth,
        double windowHeight,
        AppConfig appConfig
    ) {
        winningScoreField = new TextField(
            String.valueOf(appConfig.gamePage().winningScore())
        );
        winningScoreField.setId(WINNING_SCORE_FIELD_ID);

        ballSpeedField = new TextField(
            String.valueOf(appConfig.gamePage().ball().initialVelocityX())
        );
        ballSpeedField.setId(BALL_SPEED_FIELD_ID);

        paddleSpeedField = new TextField(
            String.valueOf(appConfig.gamePage().paddle().speed())
        );
        paddleSpeedField.setId(PADDLE_SPEED_FIELD_ID);

        saveButton = new Button("Save");
        saveButton.setId(SAVE_BUTTON_ID);

        backButton = new Button("Back");
        backButton.setId(BACK_BUTTON_ID);

        validationFeedback = new Label();
        validationFeedback.setId(VALIDATION_FEEDBACK_ID);
        validationFeedback.setVisible(false);

        root = new VBox(10);
        root.setStyle("-fx-background-color: black;");
        root.setId(OPTIONS_PAGE_ID);
        root.getChildren().addAll(
            winningScoreField,
            ballSpeedField,
            paddleSpeedField,
            validationFeedback,
            saveButton,
            backButton
        );

        scene = new Scene(root, windowWidth, windowHeight);
    }

    public void setClickOnBackButton(Runnable backAction) {
        backButton.setOnAction(event -> backAction.run());
    }

    public void setClickOnSaveButton(Runnable saveAction) {
        saveButton.setOnAction(event -> saveAction.run());
    }

    public void showValidationFeedback(String message) {
        validationFeedback.setText(message);
        validationFeedback.setVisible(true);
    }

    public void clearValidationFeedback() {
        validationFeedback.setText("");
        validationFeedback.setVisible(false);
    }

    public Scene scene() {
        return scene;
    }
}
