package com.cuseto.pong.game.model.view;

/**
 * A read-only view of a paddle's position, size, and speed.
 *
 * <p>Exposes the state a renderer or other consumer needs without granting
 * access to the mutating methods declared on the concrete {@code Paddle} type.
 */
public interface PaddleView {
    /**
     * Returns the x-coordinate of the paddle's top-left corner.
     *
     * @return the x-coordinate of the paddle's top-left corner; fixed for the paddle's lifetime
     */
    double x();
    
    /**
     * Returns the y-coordinate of the paddle's top-left corner.
     *
     * @return the y-coordinate of the paddle's top-left corner; changes as the paddle moves
     */
    double y();
    
    /**
     * Returns the paddle's width.
     *
     * @return the paddle's width; fixed for the paddle's lifetime
     */
    double width();
    
    /**
     * Returns the paddle's height.
     *
     * @return the paddle's height; fixed for the paddle's lifetime
     */
    double height();
    
    /**
     * Returns the paddle's movement speed.
     *
     * @return the paddle's movement speed, in units per second
     */
    double speed(); 
}