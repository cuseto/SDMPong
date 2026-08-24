package com.cuseto.pong.view.components;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;

/**
 * A consistently styled button for the application's Options menu.
 */
public class OptionsButton extends Button {
    private static final String DEFAULT_STYLE = """
        -fx-background-color: #33353a;
        -fx-text-fill: white;
        -fx-border-color: #f8f6f6;
        -fx-border-width: 2;
        -fx-cursor: hand;
        -fx-border-radius: 9;
        -fx-background-radius: 9;
        """;

    private static final String HOVER_STYLE = """
        -fx-background-color: #4a4d55;
        -fx-text-fill: white;
        -fx-border-color: #e94560;
        -fx-border-width: 2;
        -fx-cursor: hand;
        -fx-border-radius: 9;
        -fx-background-radius: 9;
        """;

    private static final String ACTIVE_STYLE = """
        -fx-background-color: #f8f6f6;
        -fx-text-fill: #33353a;
        -fx-border-color: #e94560;
        -fx-border-width: 2;
        -fx-cursor: hand;
        -fx-border-radius: 9;
        -fx-background-radius: 9;
        """;

    private boolean actionCompleted;

    /**
     * Creates an options-styled button with the given label.
     *
     * @param text the button's label text
     */
    public OptionsButton(String text) {
        super(text);
        applyStyle(this);
    }

    private void applyStyle(OptionsButton btn) {
        btn.setPrefSize(100, 50);
        btn.setFont(Font.font("Monospaced", 18));
        btn.hoverProperty().addListener((observable, oldValue, newValue) -> updateStyle());
        btn.pressedProperty().addListener((observable, oldValue, newValue) -> updateStyle());
        btn.focusedProperty().addListener((observable, oldValue, focused) -> {
            if (!focused) {
                actionCompleted = false;
            }
            updateStyle();
        });
        btn.addEventHandler(ActionEvent.ACTION, event -> {
            actionCompleted = true;
            updateStyle();
        });
        updateStyle();
    }

    private void updateStyle() {
        if (isPressed() || actionCompleted) {
            setStyle(ACTIVE_STYLE);
        }
        else if (isHover()) {
            setStyle(HOVER_STYLE);
        }
        else {
            setStyle(DEFAULT_STYLE);
        }
    }
}
