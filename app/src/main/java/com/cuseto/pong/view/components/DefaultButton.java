package com.cuseto.pong.view.components;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;

/**
 * A consistently styled button for the application's user interface.
 */
public class DefaultButton extends Button {
    private static final String DEFAULT_STYLE = """
        -fx-background-color: #16213e;
        -fx-text-fill: white;
        -fx-border-color: #e94560;
        -fx-border-width: 2;
        -fx-cursor: hand;
        -fx-border-radius: 9;
        -fx-background-radius: 9;
        """;

    private static final String HOVER_STYLE = """
        -fx-background-color: #263b6e;
        -fx-text-fill: white;
        -fx-border-color: #ff6b81;
        -fx-border-width: 2;
        -fx-cursor: hand;
        -fx-border-radius: 9;
        -fx-background-radius: 9;
        """;

    private static final String ACTIVE_STYLE = """
        -fx-background-color: #e94560;
        -fx-text-fill: white;
        -fx-border-color: white;
        -fx-border-width: 2;
        -fx-cursor: hand;
        -fx-border-radius: 9;
        -fx-background-radius: 9;
        """;

    private boolean actionCompleted;

    /**
     * Creates a default-styled button with the given label.
     *
     * @param text the button's label text
     */
    public DefaultButton(String text) {
        super(text);
        applyStyle(this);
    }

    private void applyStyle(DefaultButton btn) {
        btn.setPrefSize(200, 50);
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
