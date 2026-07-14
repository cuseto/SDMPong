package com.cuseto.pong.model;

/**
 * Encapsulates the ball's position and radius
 */
public record Circle (
    int startPosX,
    int startPosY,
    int radius
) {
    public Circle {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
    }
}
