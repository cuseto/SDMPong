package com.cuseto.pong.config.schema;

import com.cuseto.pong.config.validation.ConfigValidation;

/**
 * The size of the application window, in pixels.
 *
 * @param screenWidth the window's width, in pixels; must be positive
 * @param screenHeight the window's height, in pixels; must be positive
 */
public record ViewportConfig(
    int screenWidth,
    int screenHeight
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code screenWidth} or {@code screenHeight} is not positive
     */
    public ViewportConfig {
        ConfigValidation.requirePositive("screenWidth", screenWidth);
        ConfigValidation.requirePositive("screenHeight", screenHeight);
    }
}
