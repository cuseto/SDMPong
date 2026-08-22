package com.cuseto.pong.config.schema.controls;

import com.cuseto.pong.config.validation.ConfigValidation;

public record ControlsConfig(
    PaddleControlsConfig leftPaddle,
    PaddleControlsConfig rightPaddle
) {
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
