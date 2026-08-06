package com.cuseto.pong.config.schema;

import com.cuseto.pong.config.validation.ConfigValidation;

public record ViewportConfig(
    int screenWidth,
    int screenHeight
) {
    public ViewportConfig {
        ConfigValidation.requirePositive("screenWidth", screenWidth);
        ConfigValidation.requirePositive("screenHeight", screenHeight);
    }
}
