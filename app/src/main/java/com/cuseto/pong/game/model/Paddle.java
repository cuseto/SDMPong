package com.cuseto.pong.game.model;

import com.cuseto.pong.game.model.view.PaddleView;

/**
 * Encapsulates a paddle's mutable vertical position and fixed dimensions.
 *
 * <p>Position is tracked as the paddle's <strong>top-left corner</strong>.
 * Only the vertical position ({@code y}) changes after construction; a
 * paddle's horizontal position, size, and speed are fixed for its lifetime.
 */
public final class Paddle implements PaddleView {
    private final double x;
    private double y;
    private final double width;
    private final double height;
    private final double speed;

    /**
     * Creates a paddle at a fixed horizontal position with a given size and
     * movement speed.
     *
     * @param x the x-coordinate of the paddle's top-left corner; fixed for the paddle's lifetime; must be finite
     * @param y the initial y-coordinate of the paddle's top-left corner; must be finite
     * @param width the paddle's width; must be finite and greater than zero
     * @param height the paddle's height; must be finite and greater than zero
     * @param speed the paddle's movement speed, in units per second; must be finite and greater than zero
     * @throws IllegalArgumentException if any argument is not finite, or if {@code width}, {@code height}, or {@code speed} is not greater than zero
     */
    public Paddle(double x, double y, double width, double height, double speed) {
        this.x = ModelValidation.requireFinite("x", x);
        this.y = ModelValidation.requireFinite("y", y);
        this.width = ModelValidation.requirePositiveFinite("width", width);
        this.height = ModelValidation.requirePositiveFinite("height", height);
        this.speed = ModelValidation.requirePositiveFinite("speed", speed);
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
    public double width() {
        return width;
    }

    @Override
    public double height() {
        return height;
    }

    @Override
    public double speed() {
        return speed;
    }

    /**
     * Moves the paddle's top-left corner to a new y-coordinate, leaving its
     * x-coordinate unchanged.
     *
     * @param y the new y-coordinate; must be finite
     * @throws IllegalArgumentException if {@code y} is not finite
     */
    public void moveToY(double y) {
        this.y = ModelValidation.requireFinite("y", y);
    }
}
