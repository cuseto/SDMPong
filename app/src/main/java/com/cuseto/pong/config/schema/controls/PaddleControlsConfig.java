package com.cuseto.pong.config.schema.controls;

import com.cuseto.pong.config.validation.ConfigValidation;

import javafx.scene.input.KeyCode;

/**
 * The key bindings that move a single paddle up and down.
 *
 * @param up the key that moves the paddle up; must not be {@code null}, must differ from {@code down}, and must not be a reserved key (currently {@link KeyCode#ESCAPE})
 * @param down the key that moves the paddle down; must not be {@code null}, must differ from {@code up}, and must not be a reserved key (currently {@link KeyCode#ESCAPE})
 */
public record PaddleControlsConfig(
    KeyCode up,
    KeyCode down
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code up} or {@code down} is {@code null}, if they are equal, or if either is a reserved key
     */
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
