package com.cuseto.pong.view.components;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * A consistently styled button for the application's user interface.
 */
public class DefaultButton extends Button {

    public DefaultButton(String text) {
        super(text);
        applyStyle(this);
    }

    private void applyStyle(DefaultButton btn) {
        btn.setPrefSize(200, 50);
        btn.setFont(Font.font("Monospaced", 18));
        btn.setStyle("""
            -fx-background-color: #16213e;
            -fx-text-fill: white;
            -fx-border-color: #e94560;
            -fx-border-width: 2;
            -fx-cursor: hand;
            -fx-border-radius: 9;
            -fx-background-radius: 9;
            """);
    }
}
