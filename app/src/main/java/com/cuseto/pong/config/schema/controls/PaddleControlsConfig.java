package com.cuseto.pong.config.schema.controls;

import com.cuseto.pong.config.validation.ConfigValidation;

import javafx.scene.input.KeyCode;

public record PaddleControlsConfig(
    KeyCode up,
    KeyCode down
) {
    public PaddleControlsConfig {
        ConfigValidation.requireNonNull("up", up);
        ConfigValidation.requireNonNull("down", down);

        if (up == down) {
            throw new IllegalArgumentException("Paddle up and down bindings must use different keys");
        }

        if (isReservedKey(up)) {
            throw new IllegalArgumentException("Reserved key cannot be assigned as paddle control: " + up);
        }

        if (isReservedKey(down)) {
            throw new IllegalArgumentException("Reserved key cannot be assigned as paddle control: " + down);
        }
    }

    private static boolean isReservedKey(KeyCode keyCode) {
        return keyCode == KeyCode.ESCAPE;
    }
}
