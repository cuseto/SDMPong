package com.cuseto.pong.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.view.components.DefaultButton;
import com.cuseto.pong.view.components.DefaultTextField;

public final class OptionsPageRenderer {
    private static final String OPTIONS_PAGE_ID = "optionsPage";
    private static final String BACK_BUTTON_ID = "optionsBackButton";
    private static final String WINNING_SCORE_FIELD_ID = "optionsWinningScoreField";
    private static final String BALL_SPEED_FIELD_ID = "optionsBallSpeedField";
    private static final String BALL_SPEED_INCREASE_CHECKBOX_ID = "optionsBallSpeedIncreaseCheckBox";
    private static final String PADDLE_SPEED_FIELD_ID = "optionsPaddleSpeedField";
    private static final String SAVE_BUTTON_ID = "optionsSaveButton";
    private static final String VALIDATION_FEEDBACK_ID = "optionsValidationFeedback";
    private static final String LEFT_PADDLE_UP_BUTTON_ID = "optionsLeftPaddleUpButton";
    private static final String LEFT_PADDLE_DOWN_BUTTON_ID = "optionsLeftPaddleDownButton";
    private static final String RIGHT_PADDLE_UP_BUTTON_ID = "optionsRightPaddleUpButton";
    private static final String RIGHT_PADDLE_DOWN_BUTTON_ID = "optionsRightPaddleDownButton";

    private final Scene scene;
    private final VBox root;
    private final Button backButton;
    private final TextField winningScoreField;
    private final TextField ballSpeedField;
    private final CheckBox ballSpeedIncreaseCheckBox;
    private final TextField paddleSpeedField;
    private final Button leftPaddleUpButton;
    private final Button leftPaddleDownButton;
    private final Button rightPaddleUpButton;
    private final Button rightPaddleDownButton;
    private final Button saveButton;
    private final Label validationFeedback;

    private Button selectedControlButton;
    private KeyCode leftPaddleUpKey;
    private KeyCode leftPaddleDownKey;
    private KeyCode rightPaddleUpKey;
    private KeyCode rightPaddleDownKey;

    public OptionsPageRenderer(
        double windowWidth,
        double windowHeight,
        AppConfig appConfig
    ) {
        winningScoreField = new DefaultTextField(String.valueOf(appConfig.gamePage().winningScore()));
        winningScoreField.setId(WINNING_SCORE_FIELD_ID);

        ballSpeedField = new DefaultTextField(String.valueOf(appConfig.gamePage().ball().initialVelocityX()));
        ballSpeedField.setId(BALL_SPEED_FIELD_ID);

        ballSpeedIncreaseCheckBox = new CheckBox();
        ballSpeedIncreaseCheckBox.setId(BALL_SPEED_INCREASE_CHECKBOX_ID);
        ballSpeedIncreaseCheckBox.setSelected(
            appConfig.gamePage().ball().speedIncreaseEnabled()
        );

        paddleSpeedField = new DefaultTextField(String.valueOf(appConfig.gamePage().paddle().speed()));
        paddleSpeedField.setId(PADDLE_SPEED_FIELD_ID);

        leftPaddleUpButton = new DefaultButton(appConfig.controls().leftPaddle().up().toString());
        leftPaddleUpButton.setId(LEFT_PADDLE_UP_BUTTON_ID);

        leftPaddleDownButton = new DefaultButton(appConfig.controls().leftPaddle().down().toString());
        leftPaddleDownButton.setId(LEFT_PADDLE_DOWN_BUTTON_ID);

        rightPaddleUpButton = new DefaultButton(appConfig.controls().rightPaddle().up().toString());
        rightPaddleUpButton.setId(RIGHT_PADDLE_UP_BUTTON_ID);

        rightPaddleDownButton = new DefaultButton(appConfig.controls().rightPaddle().down().toString());
        rightPaddleDownButton.setId(RIGHT_PADDLE_DOWN_BUTTON_ID);

        leftPaddleUpButton.setOnAction(event -> selectControlButton(leftPaddleUpButton));
        leftPaddleDownButton.setOnAction(event -> selectControlButton(leftPaddleDownButton));
        rightPaddleUpButton.setOnAction(event -> selectControlButton(rightPaddleUpButton));
        rightPaddleDownButton.setOnAction(event -> selectControlButton(rightPaddleDownButton));

        leftPaddleUpKey = appConfig.controls().leftPaddle().up();
        leftPaddleDownKey = appConfig.controls().leftPaddle().down();
        rightPaddleUpKey = appConfig.controls().rightPaddle().up();
        rightPaddleDownKey = appConfig.controls().rightPaddle().down();

        validationFeedback = new Label();
        validationFeedback.setId(VALIDATION_FEEDBACK_ID);
        validationFeedback.setVisible(false);

        saveButton = new DefaultButton("Save");
        saveButton.setId(SAVE_BUTTON_ID);

        backButton = new DefaultButton("Back");
        backButton.setId(BACK_BUTTON_ID);

        root = new VBox(10);
        root.setStyle("-fx-background-color: black;");
        root.setId(OPTIONS_PAGE_ID);
        root.setOnKeyPressed(this::handleKeyPressed);
        root.getChildren().addAll(
            winningScoreField,
            ballSpeedField,
            ballSpeedIncreaseCheckBox,
            paddleSpeedField,
            leftPaddleUpButton,
            leftPaddleDownButton,
            rightPaddleUpButton,
            rightPaddleDownButton,
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

    public String winningScore() {
        return winningScoreField.getText();
    }

    public String ballSpeed() {
        return ballSpeedField.getText();
    }

    public boolean ballSpeedIncreaseEnabled() {
        return ballSpeedIncreaseCheckBox.isSelected();
    }

    public String paddleSpeed() {
        return paddleSpeedField.getText();
    }

    private void selectControlButton(Button button) {
        selectedControlButton = button;
    }

    public void handleKeyPressed(KeyEvent event) {
        if (selectedControlButton == null) {
            return;
        }

        KeyCode key = event.getCode();

        if (selectedControlButton == leftPaddleUpButton) {
            leftPaddleUpKey = key;
        }
        else if (selectedControlButton == leftPaddleDownButton) {
            leftPaddleDownKey = key;
        }
        else if (selectedControlButton == rightPaddleUpButton) {
            rightPaddleUpKey = key;
        }
        else if (selectedControlButton == rightPaddleDownButton) {
            rightPaddleDownKey = key;
        }

        selectedControlButton.setText(key.toString());
        selectedControlButton = null;
    }

    public KeyCode leftPaddleUpKey() {
        return leftPaddleUpKey;
    }

    public KeyCode leftPaddleDownKey() {
        return leftPaddleDownKey;
    }

    public KeyCode rightPaddleUpKey() {
        return rightPaddleUpKey;
    }

    public KeyCode rightPaddleDownKey() {
        return rightPaddleDownKey;
    }

    public Scene scene() {
        return scene;
    }
}
