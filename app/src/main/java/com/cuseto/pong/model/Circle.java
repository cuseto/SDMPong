package com.cuseto.pong.model;

/**
 * Encapsulates the ball's position and radius
 */
public record Circle (
    double startPosX,
    double startPosY,
    double radius,
    double velocityX,
    double velocityY
) {
    public Circle {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
    }

    public Circle(double startPosX, double startPosY, double radius) {
        this(startPosX, startPosY, radius, 0.0, 0.0);
    }
}
