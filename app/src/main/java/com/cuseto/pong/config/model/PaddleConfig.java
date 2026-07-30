package com.cuseto.pong.config.model;

public record PaddleConfig(
    int width,
    int height,
    int inset,
    double speed
) {
    public PaddleConfig {
        ConfigValidation.requirePositive("width", width);
        ConfigValidation.requirePositive("height", height);
        ConfigValidation.requireNonNegative("inset", inset);
        ConfigValidation.requirePositiveFinite("speed", speed);
    }
}
