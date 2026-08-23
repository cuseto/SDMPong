package com.cuseto.pong.config.schema.game;

import com.cuseto.pong.config.validation.ConfigValidation;

public record BallConfig(
    int radius,
    double initialVelocityX,
    double initialVelocityY,
    boolean speedIncreaseEnabled
) {
    public BallConfig {
        ConfigValidation.requirePositive("radius", radius);
        ConfigValidation.requireFinite("initialVelocityX", initialVelocityX);
        ConfigValidation.requireFinite("initialVelocityY", initialVelocityY);
    }
}
