package com.cuseto.pong.game.input;

import com.cuseto.pong.game.model.PaddleDirection;

/**
 * Holds the current movement direction for each paddle, as derived from
 * player input.
 *
 * <p>This is a plain mutable holder: input-handling code (e.g. key press/
 * release listeners) calls the setters to record the player's intent, and
 * {@link com.cuseto.pong.game.update.PaddleGameUpdater} reads the getters
 * once per frame to move the paddles accordingly. It has no knowledge of
 * keyboard events or configuration itself.
 */
public class PaddleInputState {
    private PaddleDirection leftDirection = PaddleDirection.NONE;
    private PaddleDirection rightDirection = PaddleDirection.NONE;

    /**
     * Returns the left paddle's current movement direction.
     *
     * @return the left paddle's current movement direction; {@link PaddleDirection#NONE} if not moving
     */
    public PaddleDirection leftDirection() {
        return leftDirection;
    }

    /**
     * Returns the right paddle's current movement direction.
     *
     * @return the right paddle's current movement direction; {@link PaddleDirection#NONE} if not moving
     */
    public PaddleDirection rightDirection() {
        return rightDirection;
    }

    /**
     * Sets the left paddle's current movement direction.
     *
     * @param direction the left paddle's new movement direction
     */
    public void setLeftDirection(PaddleDirection direction) {
        this.leftDirection = direction;
    }

    /**
     * Sets the right paddle's current movement direction.
     *
     * @param direction the right paddle's new movement direction
     */
    public void setRightDirection(PaddleDirection direction) {
        this.rightDirection = direction;
    }
}
