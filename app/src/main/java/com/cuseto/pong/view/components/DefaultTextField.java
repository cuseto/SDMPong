package com.cuseto.pong.view.components;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * A consistently styled text field for the application's user interface.
 */
public final class DefaultTextField extends TextField {
    private static final double FIELD_WIDTH = 400;
    private static final double FIELD_HEIGHT = 24;

    public DefaultTextField(String text) {
        super(text);
        applyStyle(this);
    }
        
    private void applyStyle(DefaultTextField txtfld) {
        txtfld.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
        txtfld.setMaxWidth(FIELD_WIDTH);
        txtfld.setFont(Font.font("Monospaced", 18));
        txtfld.setStyle("""
            -fx-background-color: #33353a;
            -fx-text-fill: white;
            -fx-border-color: #f8f6f6;
            -fx-border-width: 2;
            -fx-border-radius: 9;
            -fx-background-radius: 9;
            -fx-alignment: center;
            """);
    }
}
