package com.cuseto.pong.config.schema.game;

import com.cuseto.pong.config.validation.ConfigValidation;

/**
 * The configurable size, arena inset, and speed for each in-game
 * {@link com.cuseto.pong.game.model.Paddle}.
 *
 * @param width the paddle's width, in pixels; must be positive
 * @param height the paddle's height, in pixels; must be positive
 * @param inset the gap, in pixels, between the paddle and the arena's inner left/right boundary; must not be negative
 * @param speed the paddle's movement speed, in units per second; must be positive and finite
 */
public record PaddleConfig(
    int width,
    int height,
    int inset,
    double speed
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code width} or {@code height} is not positive, if {@code inset} is negative, or if {@code speed} is not positive and finite
     */
    public PaddleConfig {
        ConfigValidation.requirePositive("width", width);
        ConfigValidation.requirePositive("height", height);
        ConfigValidation.requireNonNegative("inset", inset);
        ConfigValidation.requirePositiveFinite("speed", speed);
    }
}
