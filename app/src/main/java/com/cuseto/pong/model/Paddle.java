package com.cuseto.pong.model;

/**
 * Encapsulates a paddle's mutable vertical position and fixed dimensions.
 */
public final class Paddle {
    private final double x;
    private double y;
    private final double width;
    private final double height;
    private double speed; 

    public Paddle(double x, double y, double width, double height, double speed) {
        if (width <= 0) {
            throw new IllegalArgumentException("Paddle width must be positive");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Paddle height must be positive");
        }

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public double speed() {
        return speed;
    }

    public void moveToY(double y) {
        this.y = y;
    }
}
