package com.cuseto.pong.model;

/**
 * Encapsulates the paddles' positions and dimensions
 */
public record Rectangle(
    double startPosX,
    double startPosY,
    double width,
    double height
) {
    public Rectangle {
        if (width <= 0) {
            throw new IllegalArgumentException("Paddle width must be positive");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Paddle height must be positive");
        }
    }
}
