package com.cuseto.pong.view.components;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * A consistently styled button for the application's user interface.
 */
public class OptionsButton extends Button {

    public OptionsButton(String text) {
        super(text);
        applyStyle(this);
    }

    private void applyStyle(OptionsButton btn) {
        btn.setPrefSize(100, 50);
        btn.setFont(Font.font("Monospaced", 18));
        btn.setStyle("""
            -fx-background-color: #33353a;
            -fx-text-fill: white;
            -fx-border-color: #f8f6f6;
            -fx-border-width: 2;
            -fx-cursor: hand;
            -fx-border-radius: 9;
            -fx-background-radius: 9;
            """);
    }
}
