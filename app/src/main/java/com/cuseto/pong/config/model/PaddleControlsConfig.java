package com.cuseto.pong.config.model;

import javafx.scene.input.KeyCode;

public record PaddleControlsConfig(
    KeyCode up,
    KeyCode down
) {
    public PaddleControlsConfig {
        ConfigValidation.requireNonNull("up", up);
        ConfigValidation.requireNonNull("down", down);
        if (up == down) {
            throw new IllegalArgumentException("up and down must use different keys");
        }
    }
}
