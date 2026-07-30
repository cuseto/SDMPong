package com.cuseto.pong.config.model;

public record ControlsConfig(
    PaddleControlsConfig leftPaddle,
    PaddleControlsConfig rightPaddle
) {
    public ControlsConfig {
        ConfigValidation.requireNonNull("leftPaddle", leftPaddle);
        ConfigValidation.requireNonNull("rightPaddle", rightPaddle);
    }
}
