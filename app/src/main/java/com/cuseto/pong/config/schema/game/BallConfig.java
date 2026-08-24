package com.cuseto.pong.config.schema.game;

import com.cuseto.pong.config.validation.ConfigValidation;

/**
 * The configurable size, initial velocity, and speed-up behavior for the
 * in-game {@link com.cuseto.pong.game.model.Ball}.
 *
 * @param radius the ball's radius, in pixels; must be positive
 * @param initialVelocityX the ball's horizontal velocity at the start of each round, in units per second; must be finite
 * @param initialVelocityY the ball's vertical velocity at the start of each round, in units per second; must be finite
 * @param speedIncreaseEnabled whether the ball's horizontal speed increases on each paddle bounce
 */
public record BallConfig(
    int radius,
    double initialVelocityX,
    double initialVelocityY,
    boolean speedIncreaseEnabled
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code radius} is not positive, or if {@code initialVelocityX} or {@code initialVelocityY} is not finite
     */
    public BallConfig {
        ConfigValidation.requirePositive("radius", radius);
        ConfigValidation.requireFinite("initialVelocityX", initialVelocityX);
        ConfigValidation.requireFinite("initialVelocityY", initialVelocityY);
    }
}
