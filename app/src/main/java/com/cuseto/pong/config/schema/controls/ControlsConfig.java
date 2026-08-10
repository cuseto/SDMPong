package com.cuseto.pong.config.schema.controls;

import com.cuseto.pong.config.validation.ConfigValidation;

public record ControlsConfig(
    PaddleControlsConfig leftPaddle,
    PaddleControlsConfig rightPaddle
) {
    public ControlsConfig {
        ConfigValidation.requireNonNull("leftPaddle", leftPaddle);
        ConfigValidation.requireNonNull("rightPaddle", rightPaddle);
    }
}
