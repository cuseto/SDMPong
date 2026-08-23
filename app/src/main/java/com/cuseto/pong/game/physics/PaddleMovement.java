package com.cuseto.pong.game.physics;

import com.cuseto.pong.game.model.Paddle;
import com.cuseto.pong.game.model.PaddleDirection;

/**
 * Pure physics for advancing a paddle's vertical position by one frame,
 * based on its current movement direction.
 */
public final class PaddleMovement {
    private PaddleMovement() {
    }

    /**
     * Moves the paddle vertically by {@code speed * elapsedSeconds} in the
     * given direction, clamping the result so the paddle's top-left corner
     * stays within {@code [minY, maxY]}.
     *
     * @param paddle the paddle to move; its position is mutated in place
     * @param direction the direction to move in; {@link PaddleDirection#NONE} leaves the paddle's position unchanged
     * @param elapsedSeconds the amount of simulated time to advance, in seconds
     * @param minY the lowest allowed y-coordinate for the paddle's top-left corner
     * @param maxY the highest allowed y-coordinate for the paddle's top-left corner; callers typically pass the arena's inner bottom boundary minus the paddle's height, so the whole paddle stays inside the arena
     */
    public static void move(
        Paddle paddle,
        PaddleDirection direction,
        double elapsedSeconds,
        double minY,
        double maxY
    ) {
        double delta = paddle.speed() * elapsedSeconds;
        double newY = paddle.y();

        if (direction == PaddleDirection.UP) {
            newY -= delta;
        } else if (direction == PaddleDirection.DOWN) {
            newY += delta;
        }

        newY = Math.max(minY, Math.min(maxY, newY));

        paddle.moveToY(newY);
    }
}
