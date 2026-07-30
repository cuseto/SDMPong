package com.cuseto.pong.config.model;

public record ViewportConfig(
    int screenWidth,
    int screenHeight
) {
    public ViewportConfig {
        ConfigValidation.requirePositive("screenWidth", screenWidth);
        ConfigValidation.requirePositive("screenHeight", screenHeight);
    }
}
