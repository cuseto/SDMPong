package com.cuseto.pong.game.model.view;

/**
 * A read-only view of a paddle's position, size, and speed.
 *
 * <p>Exposes the state a renderer or other consumer needs without granting
 * access to the mutating methods declared on the concrete {@code Paddle} type.
 */
public interface PaddleView {
    /** @return the x-coordinate of the paddle's top-left corner; fixed for the paddle's lifetime */
    double x();
    /** @return the y-coordinate of the paddle's top-left corner; changes as the paddle moves */
    double y();
    /** @return the paddle's width; fixed for the paddle's lifetime */
    double width();
    /** @return the paddle's height; fixed for the paddle's lifetime */
    double height();
    /** @return the paddle's movement speed, in units per second */
    double speed(); 
}