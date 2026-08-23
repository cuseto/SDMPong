package com.cuseto.pong.game.model.view;

/**
 * A read-only view of a ball's position, size, and velocity.
 *
 * <p>Exposes the state a renderer or other consumer needs without granting
 * access to the mutating methods declared on the concrete {@code Ball} type.
 */
public interface BallView {
    /** @return the x-coordinate of the ball's center */
    double x();
    /** @return the y-coordinate of the ball's center */
    double y();
    /** @return the ball's radius; always positive */
    double radius();
    /** @return the ball's current horizontal velocity, in units per second */
    double velocityX();
    /** @return the ball's current vertical velocity, in units per second */
    double velocityY();
}