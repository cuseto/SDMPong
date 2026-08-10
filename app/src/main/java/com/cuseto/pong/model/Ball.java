package com.cuseto.pong.model;

import com.cuseto.pong.model.view.BallView;

/**
 * Encapsulates the ball's mutable position and velocity.
 */
public final class Ball implements BallView {
    private double x;
    private double y;
    private final double radius;
    private double velocityX;
    private double velocityY;

    public Ball(double x, double y, double radius, double velocityX, double velocityY) {
        this.x = ModelValidation.requireFinite("x", x);
        this.y = ModelValidation.requireFinite("y", y);
        this.radius = ModelValidation.requirePositiveFinite("radius", radius);
        this.velocityX = ModelValidation.requireFinite("velocityX", velocityX);
        this.velocityY = ModelValidation.requireFinite("velocityY", velocityY);
    }

    public Ball(double x, double y, double radius) {
        this(x, y, radius, 0.0, 0.0);
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double radius() {
        return radius;
    }

    @Override
    public double velocityX() {
        return velocityX;
    }

    @Override
    public double velocityY() {
        return velocityY;
    }

    public void moveTo(double x, double y) {
        double validatedX = ModelValidation.requireFinite("x", x);
        double validatedY = ModelValidation.requireFinite("y", y);
        this.x = validatedX;
        this.y = validatedY;
    }

    public void setX(double newX) {
        this.x = ModelValidation.requireFinite("x", newX);
    }

    public void setY(double newY) {
        this.y = ModelValidation.requireFinite("y", newY);
    }

    public void setVelocity(double velocityX, double velocityY) {
        double validatedVelocityX = ModelValidation.requireFinite("velocityX", velocityX);
        double validatedVelocityY = ModelValidation.requireFinite("velocityY", velocityY);
        this.velocityX = validatedVelocityX;
        this.velocityY = validatedVelocityY;
    }
}
