package com.cuseto.pong.view.components;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * Base class for application buttons with shared interaction behaviour.
 */
abstract class StyledButton extends Button {
    private final ButtonAppearance appearance;
    private boolean actionCompleted;

    StyledButton(String text, ButtonAppearance appearance) {
        super(text);
        this.appearance = appearance;

        setPrefSize(appearance.preferredWidth(), appearance.preferredHeight());
        setFont(Font.font("Monospaced", 18));
        hoverProperty().addListener((observable, oldValue, newValue) -> updateStyle());
        pressedProperty().addListener((observable, oldValue, newValue) -> updateStyle());
        focusedProperty().addListener((observable, oldValue, focused) -> {
            if (!focused) {
                actionCompleted = false;
            }
            updateStyle();
        });
        addEventHandler(ActionEvent.ACTION, event -> {
            actionCompleted = true;
            updateStyle();
        });
        updateStyle();
    }

    private void updateStyle() {
        if (isPressed() || actionCompleted) {
            setStyle(appearance.activeStyle());
        }
        else if (isHover()) {
            setStyle(appearance.hoverStyle());
        }
        else {
            setStyle(appearance.defaultStyle());
        }
    }
}
