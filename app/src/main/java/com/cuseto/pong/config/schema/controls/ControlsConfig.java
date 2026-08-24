package com.cuseto.pong.config.schema.controls;

import com.cuseto.pong.config.validation.ConfigValidation;

/**
 * The key bindings for both paddles.
 *
 * @param leftPaddle the left paddle's key bindings; must not be {@code null}, and must not share any key with {@code rightPaddle}
 * @param rightPaddle the right paddle's key bindings; must not be {@code null}, and must not share any key with {@code leftPaddle}
 */
public record ControlsConfig(
    PaddleControlsConfig leftPaddle,
    PaddleControlsConfig rightPaddle
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code leftPaddle} or {@code rightPaddle} is {@code null}, or if they share an up/down key binding
     */
    public ControlsConfig {
        ConfigValidation.requireNonNull("leftPaddle", leftPaddle);
        ConfigValidation.requireNonNull("rightPaddle", rightPaddle);

        if (hasDuplicateBinding(leftPaddle, rightPaddle)) {
            throw new IllegalArgumentException(
                "Paddle control bindings must be unique"
            );
        }
    }

    private static boolean hasDuplicateBinding(
        PaddleControlsConfig left,
        PaddleControlsConfig right
    ) {
        return left.up() == right.up()
            || left.up() == right.down()
            || left.down() == right.up()
            || left.down() == right.down();
    }
}
