package com.cuseto.pong.model;

import com.cuseto.pong.model.view.PaddleView;

/**
 * Encapsulates a paddle's mutable vertical position and fixed dimensions.
 */
public final class Paddle implements PaddleView{
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
        this.y = y;
    }
}
