package com.cuseto.pong.model;

/**
 * Encapsulates the ball's mutable position and velocity.
 */
public final class Ball {
    private double x;
    private double y;
    private final double radius;
    private double velocityX;
    private double velocityY;

    public Ball(double x, double y, double radius, double velocityX, double velocityY) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }

        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public Ball(double x, double y, double radius) {
        this(x, y, radius, 0.0, 0.0);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double radius() {
        return radius;
    }

    public double velocityX() {
        return velocityX;
    }

    public double velocityY() {
        return velocityY;
    }

    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
}
