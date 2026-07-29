package com.cuseto.pong.model;

/**
 * Encapsulates the ball's position and radius
 */
public record Ball (
    double x,
    double y,
    double radius,
    double velocityX,
    double velocityY
) {
    public Ball {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
    }

    public Ball(double x, double y, double radius) {
        this(x, y, radius, 0.0, 0.0);
    }
}
