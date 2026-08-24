package com.cuseto.pong.view.components;

import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A consistently styled checkbox for the application's user interface.
 */
public final class DefaultCheckBox extends CheckBox {
    /**
     * Creates a default-styled checkbox with the given label.
     *
     * @param text the checkbox's label text
     */
    public DefaultCheckBox(String text) {
        super(text);
        setTextFill(Color.WHITE);
        setFont(Font.font("Monospaced", 18));
        setStyle("""
            -fx-cursor: hand;
            -fx-padding: 8 0 8 0;
            """);
    }
}
