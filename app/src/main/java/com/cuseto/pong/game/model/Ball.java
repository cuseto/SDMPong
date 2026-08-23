package com.cuseto.pong.game.model;

import com.cuseto.pong.game.model.view.BallView;

/**
 * Encapsulates the ball's mutable position and velocity.
 *
 * <p>Position is tracked as the ball's <strong>center</strong> point, with a
 * fixed radius. Velocity is expressed in units per second and is expected to
 * be updated by the physics layer as the ball moves and bounces; it is
 * distinct from any fixed "seed" velocity used only to initialize a new ball.
 */
public final class Ball implements BallView {
    private double x;
    private double y;
    private final double radius;
    private double velocityX;
    private double velocityY;

    /**
     * Creates a ball with an explicit initial velocity.
     *
     * @param x the x-coordinate of the ball's center
     * @param y the y-coordinate of the ball's center
     * @param radius the ball's radius; must be finite and greater than zero
     * @param velocityX the initial horizontal velocity, in units per second; must be finite
     * @param velocityY the initial vertical velocity, in units per second; must be finite
     * @throws IllegalArgumentException if any argument is not finite, or if {@code radius} is not greater than zero
     */
    public Ball(double x, double y, double radius, double velocityX, double velocityY) {
        this.x = ModelValidation.requireFinite("x", x);
        this.y = ModelValidation.requireFinite("y", y);
        this.radius = ModelValidation.requirePositiveFinite("radius", radius);
        this.velocityX = ModelValidation.requireFinite("velocityX", velocityX);
        this.velocityY = ModelValidation.requireFinite("velocityY", velocityY);
    }

    /**
     * Creates a stationary ball, with velocity {@code (0, 0)}.
     *
     * @param x      the x-coordinate of the ball's center
     * @param y      the y-coordinate of the ball's center
     * @param radius the ball's radius; must be finite and greater than zero
     * @throws IllegalArgumentException if any argument is not finite, or if {@code radius} is not greater than zero
     */
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

    /**
     * Moves the ball's center to a new position.
     *
     * @param x the new x-coordinate; must be finite
     * @param y the new y-coordinate; must be finite
     * @throws IllegalArgumentException if {@code x} or {@code y} is not finite
     */
    public void moveTo(double x, double y) {
        double validatedX = ModelValidation.requireFinite("x", x);
        double validatedY = ModelValidation.requireFinite("y", y);
        this.x = validatedX;
        this.y = validatedY;
    }

    /**
     * Sets the x-coordinate of the ball's center.
     *
     * @param newX the new x-coordinate; must be finite
     * @throws IllegalArgumentException if {@code newX} is not finite
     */
    public void setX(double newX) {
        this.x = ModelValidation.requireFinite("x", newX);
    }

    /**
     * Sets the y-coordinate of the ball's center.
     *
     * @param newY the new y-coordinate; must be finite
     * @throws IllegalArgumentException if {@code newY} is not finite
     */
    public void setY(double newY) {
        this.y = ModelValidation.requireFinite("y", newY);
    }

    /**
     * Sets the ball's velocity, e.g. after a bounce reverses one or both
     * components.
     *
     * @param velocityX the new horizontal velocity, in units per second; must be finite
     * @param velocityY the new vertical velocity, in units per second; must be finite
     * @throws IllegalArgumentException if {@code velocityX} or {@code velocityY} is not finite
     */
    public void setVelocity(double velocityX, double velocityY) {
        double validatedVelocityX = ModelValidation.requireFinite("velocityX", velocityX);
        double validatedVelocityY = ModelValidation.requireFinite("velocityY", velocityY);
        this.velocityX = validatedVelocityX;
        this.velocityY = validatedVelocityY;
    }
}
