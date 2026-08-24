package com.cuseto.pong.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.view.components.DefaultButton;
import com.cuseto.pong.view.components.OptionsButton;
import com.cuseto.pong.view.components.DefaultCheckBox;
import com.cuseto.pong.view.components.DefaultTextField;

/**
 * Builds and drives the options screen: editable fields for match
 * settings, rebindable paddle control buttons, and save/back actions.
 *
 * <p>Fields and control buttons are pre-filled from the {@link AppConfig}
 * passed to the constructor. Text field and checkbox values are read
 * as-entered via {@link #winningScore()}, {@link #ballSpeed()}, {@link
 * #paddleSpeed()}, and {@link #ballSpeedIncreaseEnabled()}; this class
 * performs no parsing or validation itself, leaving that to the
 * controller that reads these values on save.
 *
 * <p><b>Control rebinding:</b> clicking one of the four paddle control
 * buttons selects it for rebinding; the next key press anywhere in the
 * scene is captured as that control's new key, updates the button's
 * label, and clears the selection, consuming the event so it is not also
 * processed as game input. Only one control button can be selected for
 * rebinding at a time. The rebound keys are exposed via {@link
 * #leftPaddleUpKey()}, {@link #leftPaddleDownKey()}, {@link
 * #rightPaddleUpKey()}, and {@link #rightPaddleDownKey()}; these reflect
 * pending, unsaved edits, not necessarily the keys in the original
 * {@code AppConfig}.
 */
public final class OptionsPageRenderer {
    private static final String OPTIONS_PAGE_ID = "optionsPage";
    private static final String OPTIONS_TITLE_ID = "optionsTitle";
    private static final String BACK_BUTTON_ID = "optionsBackButton";
    private static final String WINNING_SCORE_FIELD_ID = "optionsWinningScoreField";
    private static final String WINNING_SCORE_LABEL_ID = "optionsWinningScoreLabel";
    private static final String BALL_SPEED_FIELD_ID = "optionsBallSpeedField";
    private static final String BALL_SPEED_LABEL_ID = "optionsBallSpeedLabel";
    private static final String BALL_SPEED_INCREASE_CHECKBOX_ID = "optionsBallSpeedIncreaseCheckBox";
    private static final String PADDLE_SPEED_FIELD_ID = "optionsPaddleSpeedField";
    private static final String PADDLE_SPEED_LABEL_ID = "optionsPaddleSpeedLabel";
    private static final String KEY_BINDINGS_TITLE_ID = "optionsKeyBindingsTitle";
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

    /**
     * Builds the options screen, pre-filled with the given configuration's
     * match settings and control bindings, with an event filter installed
     * to capture key presses for control rebinding.
     *
     * @param windowWidth the scene width, in pixels
     * @param windowHeight the scene height, in pixels
     * @param appConfig the configuration used to pre-fill the screen's fields and control buttons; must not be {@code null}
     */
    public OptionsPageRenderer(
        double windowWidth,
        double windowHeight,
        AppConfig appConfig
    ) {
        winningScoreField = new DefaultTextField(String.valueOf(appConfig.gamePage().winningScore()));
        winningScoreField.setId(WINNING_SCORE_FIELD_ID);

        ballSpeedField = new DefaultTextField(String.valueOf(appConfig.gamePage().ball().initialVelocityX()));
        ballSpeedField.setId(BALL_SPEED_FIELD_ID);

        ballSpeedIncreaseCheckBox = new DefaultCheckBox(
            "Speed increase on paddle bounce"
        );
        ballSpeedIncreaseCheckBox.setId(BALL_SPEED_INCREASE_CHECKBOX_ID);
        ballSpeedIncreaseCheckBox.setSelected(
            appConfig.gamePage().ball().speedIncreaseEnabled()
        );

        paddleSpeedField = new DefaultTextField(String.valueOf(appConfig.gamePage().paddle().speed()));
        paddleSpeedField.setId(PADDLE_SPEED_FIELD_ID);

        leftPaddleUpButton = new OptionsButton(appConfig.controls().leftPaddle().up().toString());
        leftPaddleUpButton.setId(LEFT_PADDLE_UP_BUTTON_ID);

        leftPaddleDownButton = new OptionsButton(appConfig.controls().leftPaddle().down().toString());
        leftPaddleDownButton.setId(LEFT_PADDLE_DOWN_BUTTON_ID);

        rightPaddleUpButton = new OptionsButton(appConfig.controls().rightPaddle().up().toString());
        rightPaddleUpButton.setId(RIGHT_PADDLE_UP_BUTTON_ID);

        rightPaddleDownButton = new OptionsButton(appConfig.controls().rightPaddle().down().toString());
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
        validationFeedback.setTextFill(Color.RED);
        validationFeedback.setVisible(false);

        saveButton = new DefaultButton("Save");
        saveButton.setId(SAVE_BUTTON_ID);

        backButton = new DefaultButton("Back");
        backButton.setId(BACK_BUTTON_ID);

        Label title = new Label("Options");
        title.setId(OPTIONS_TITLE_ID);
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Monospaced", FontWeight.BOLD, 64));

        HBox paddleControls = new HBox(
            128,
            createPaddleControlsColumn(
                "P1",
                leftPaddleUpButton,
                leftPaddleDownButton
            ),
            createPaddleControlsColumn(
                "P2",
                rightPaddleUpButton,
                rightPaddleDownButton
            )
        );
        paddleControls.setAlignment(Pos.CENTER);

        Label keyBindingsTitle = new Label("Key Bindings");
        keyBindingsTitle.setId(KEY_BINDINGS_TITLE_ID);
        keyBindingsTitle.setTextFill(Color.WHITE);
        keyBindingsTitle.setFont(Font.font("Monospaced", 16));

        VBox keyBindings = new VBox(0, keyBindingsTitle, paddleControls);
        keyBindings.setAlignment(Pos.CENTER);

        VBox winningScoreSetting = createSettingField(
            "Winning score",
            WINNING_SCORE_LABEL_ID,
            winningScoreField
        );
        VBox ballSpeedSetting = createSettingField(
            "Ball speed",
            BALL_SPEED_LABEL_ID,
            ballSpeedField
        );
        VBox paddleSpeedSetting = createSettingField(
            "Paddle speed",
            PADDLE_SPEED_LABEL_ID,
            paddleSpeedField
        );

        VBox menuContent = new VBox(
            15,
            title,
            winningScoreSetting,
            ballSpeedSetting,
            ballSpeedIncreaseCheckBox,
            paddleSpeedSetting,
            keyBindings,
            validationFeedback
        );
        menuContent.setAlignment(Pos.CENTER);

        HBox actionButtons = new HBox(24, saveButton, backButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(0, 0, 24, 0));

        root = new VBox(10);
        root.setFocusTraversable(true);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");
        root.setId(OPTIONS_PAGE_ID);
        root.getChildren().addAll(
            menuContent,
            actionButtons
        );
        VBox.setVgrow(menuContent, Priority.ALWAYS);
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getTarget() == root) {
                root.requestFocus();
            }
        });

        scene = new Scene(root, windowWidth, windowHeight);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }

    /**
     * Sets the action run when the back button is clicked.
     *
     * @param backAction the callback to run
     */
    public void setClickOnBackButton(Runnable backAction) {
        backButton.setOnAction(event -> backAction.run());
    }

    /**
     * Sets the action run when the save button is clicked.
     *
     * @param saveAction the callback to run
     */
    public void setClickOnSaveButton(Runnable saveAction) {
        saveButton.setOnAction(event -> saveAction.run());
    }

    /**
     * Shows the given message as validation feedback, e.g. after a save
     * attempt fails.
     *
     * @param message the feedback message to display
     */
    public void showValidationFeedback(String message) {
        validationFeedback.setText(message);
        validationFeedback.setVisible(true);
    }

    /**
     * Clears and hides any validation feedback currently shown, e.g. after
     * a successful save.
     */
    public void clearValidationFeedback() {
        validationFeedback.setText("");
        validationFeedback.setVisible(false);
    }

    /**
     * Returns the winning-score field's current text, as entered by the
     * player and not yet parsed or validated.
     *
     * @return the winning-score field's raw text
     */
    public String winningScore() {
        return winningScoreField.getText();
    }

    /**
     * Returns the ball-speed field's current text, as entered by the
     * player and not yet parsed or validated.
     *
     * @return the ball-speed field's raw text
     */
    public String ballSpeed() {
        return ballSpeedField.getText();
    }

    /**
     * Returns whether the ball-speed-increase checkbox is currently checked.
     *
     * @return {@code true} if the checkbox is checked, {@code false} otherwise
     */
    public boolean ballSpeedIncreaseEnabled() {
        return ballSpeedIncreaseCheckBox.isSelected();
    }

    /**
     * Returns the paddle-speed field's current text, as entered by the
     * player and not yet parsed or validated.
     *
     * @return the paddle-speed field's raw text
     */
    public String paddleSpeed() {
        return paddleSpeedField.getText();
    }

    private void selectControlButton(Button button) {
        selectedControlButton = button;
    }

    private VBox createPaddleControlsColumn(
        String playerName,
        Button upButton,
        Button downButton
    ) {
        Label playerLabel = new Label(playerName);
        playerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");

        VBox controlsColumn = new VBox(10, playerLabel, upButton, downButton);
        controlsColumn.setAlignment(Pos.CENTER);
        return controlsColumn;
    }

    private VBox createSettingField(
        String settingName,
        String labelId,
        TextField field
    ) {
        Label settingLabel = new Label(settingName);
        settingLabel.setId(labelId);
        settingLabel.setTextFill(Color.WHITE);
        settingLabel.setFont(Font.font("Monospaced", 16));

        VBox setting = new VBox(4, settingLabel, field);
        setting.setAlignment(Pos.CENTER);
        return setting;
    }

    /**
     * Captures a key press for control rebinding, if a control button is
     * currently selected.
     *
     * <p>If no control button is selected, this does nothing. Otherwise,
     * it records the pressed key as the selected control's new binding,
     * updates the button's label to match, clears the selection, and
     * consumes the event so it is not processed further (e.g. as game
     * input).
     *
     * @param event the key-press event to handle
     */
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
        event.consume();
    }

    /**
     * Returns the left paddle's current up-key binding, including any
     * pending, unsaved rebind.
     *
     * @return the left paddle's up-key binding
     */
    public KeyCode leftPaddleUpKey() {
        return leftPaddleUpKey;
    }

    /**
     * Returns the left paddle's current down-key binding, including any
     * pending, unsaved rebind.
     *
     * @return the left paddle's down-key binding
     */
    public KeyCode leftPaddleDownKey() {
        return leftPaddleDownKey;
    }

    /**
     * Returns the right paddle's current up-key binding, including any
     * pending, unsaved rebind.
     *
     * @return the right paddle's up-key binding
     */
    public KeyCode rightPaddleUpKey() {
        return rightPaddleUpKey;
    }

    /**
     * Returns the right paddle's current down-key binding, including any
     * pending, unsaved rebind.
     *
     * @return the right paddle's down-key binding
     */
    public KeyCode rightPaddleDownKey() {
        return rightPaddleDownKey;
    }

    /**
     * Returns the options screen scene, for attaching to the application
     * window.
     *
     * @return the options screen scene
     */
    public Scene scene() {
        return scene;
    }
}
