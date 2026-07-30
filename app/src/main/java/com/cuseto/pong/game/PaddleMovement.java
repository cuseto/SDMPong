package com.cuseto.pong.game;

import com.cuseto.pong.model.PaddleDirection;
import com.cuseto.pong.model.Paddle;

public final class PaddleMovement {
    private PaddleMovement() {
    }

    public static void move(
        Paddle paddle,
        PaddleDirection direction,
        double elapsedSeconds,
        double speed,
        double minY,
        double maxY
    ) {
        double delta = speed * elapsedSeconds;
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
