package com.cuseto.pong.config.model;

public record BallConfig(
    int radius,
    double initialVelocityX,
    double initialVelocityY
) {
    public BallConfig {
        ConfigValidation.requirePositive("radius", radius);
        ConfigValidation.requireFinite("initialVelocityX", initialVelocityX);
        ConfigValidation.requireFinite("initialVelocityY", initialVelocityY);
    }
}
