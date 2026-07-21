package com.cuseto.pong.game;

import com.cuseto.pong.model.Circle;

public final class BallMovement {
    private BallMovement() {
    }

    public static Circle move(
        Circle ball,
        double elapsedSeconds
    ) {
        double newX = ball.startPosX() + ball.velocityX() * elapsedSeconds;
        double newY = ball.startPosY() + ball.velocityY() * elapsedSeconds;

        return new Circle(newX, newY, ball.radius(), ball.velocityX(), ball.velocityY());
    }
}
