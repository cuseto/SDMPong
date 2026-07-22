package com.cuseto.pong.game;

import com.cuseto.pong.model.Circle;

public final class BallMovement {
    private BallMovement() {
    }

    public static Circle move(
        Circle ball,
        double elapsedSeconds,
        int minY,
        int maxY
    ) {
        double newX = ball.startPosX() + ball.velocityX() * elapsedSeconds;
        double newY = ball.startPosY() + ball.velocityY() * elapsedSeconds;
        double velocityY = ball.velocityY();

        if (newY < minY) {
            newY = minY + (minY - newY);
            velocityY *= -1;
        }

        if (newY > maxY) newY = maxY - (newY - maxY);


        return new Circle(newX, newY, ball.radius(), ball.velocityX(), velocityY);
    }
}
