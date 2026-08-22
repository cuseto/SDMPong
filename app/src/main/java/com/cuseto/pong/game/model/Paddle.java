package com.cuseto.pong.game.model;

import com.cuseto.pong.game.model.view.PaddleView;

/**
 * Encapsulates a paddle's mutable vertical position and fixed dimensions.
 */
public final class Paddle implements PaddleView {
    private final double x;
    private double y;
    private final double width;
    private final double height;
    private final double speed;

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

    public void moveToY(double y) {
        this.y = ModelValidation.requireFinite("y", y);
    }
}
