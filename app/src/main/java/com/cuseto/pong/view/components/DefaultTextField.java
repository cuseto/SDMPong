package com.cuseto.pong.view.components;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 * A consistently styled text field for the application's user interface.
 */
public final class DefaultTextField extends TextField {
    private static final double FIELD_WIDTH = 400;
    private static final double FIELD_HEIGHT = 24;
    private static final String DEFAULT_STYLE = """
        -fx-background-color: #33353a;
        -fx-text-fill: white;
        -fx-border-color: #f8f6f6;
        -fx-border-width: 2;
        -fx-border-radius: 9;
        -fx-background-radius: 9;
        -fx-alignment: center;
        """;

    private static final String HOVER_STYLE = """
        -fx-background-color: #4a4d55;
        -fx-text-fill: white;
        -fx-border-color: #e94560;
        -fx-border-width: 2;
        -fx-border-radius: 9;
        -fx-background-radius: 9;
        -fx-alignment: center;
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

    private boolean clicked;

    /**
     * Creates a default-styled text field with the given initial text.
     *
     * @param text the text field's initial content
     */
    public DefaultTextField(String text) {
        super(text);
        applyStyle(this);
    }
        
    private void applyStyle(DefaultTextField txtfld) {
        txtfld.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
        txtfld.setMaxWidth(FIELD_WIDTH);
        txtfld.setFont(Font.font("Monospaced", 18));
        txtfld.hoverProperty().addListener((observable, oldValue, newValue) -> updateStyle());
        txtfld.focusedProperty().addListener((observable, oldValue, focused) -> {
            if (!focused) {
                clicked = false;
            }
            updateStyle();
        });
        txtfld.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            clicked = true;
            updateStyle();
        });
        updateStyle();
    }

    private void updateStyle() {
        if (clicked) {
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
