package com.cuseto.pong.game;

import com.cuseto.pong.model.Circle;
import com.cuseto.pong.model.Rectangle;

public final class BallMovement {
    private BallMovement() {
    }

    public static Circle move(
        Circle ball,
        double elapsedSeconds,
        int minY,
        int maxY,
        Rectangle leftPaddle,
        Rectangle rightPaddle
    ) {
        double velocityY = ball.velocityY();
        double velocityX = ball.velocityX();

        // basic ball update
        double newX = ball.startPosX() + ball.velocityX() * elapsedSeconds;
        double newY = ball.startPosY() + ball.velocityY() * elapsedSeconds;

        // checking arena bounce
        if (newY <= minY || newY >= maxY) {
            if (newY <= minY) newY = minY + (minY - newY);
            if (newY >= maxY) newY = maxY - (newY - maxY);
            velocityY *= -1;
        }

        // checking paddles bounce
        double minX = leftPaddle.startPosX() + leftPaddle.width();
        double maxX = rightPaddle.startPosX();

        if (newX <= minX & ball.startPosX() > minX) {
            if (ballCrossedPaddle(ball, leftPaddle, newX, newY)) {
                newX = minX + (minX - newX);
                velocityX *= -1;
            }
        }

        if (ball.startPosX() < maxX & newX >= maxX) {
            if (ballCrossedPaddle(ball, rightPaddle, newX, newY)) {
                newX = maxX - (newX - maxX);
                velocityX *= -1;
            }
        }

        return new Circle(newX, newY, ball.radius(), velocityX, velocityY);
    }

    private static boolean ballCrossedPaddle(Circle ball, Rectangle paddle, double newX, double newY) {
        // take the Y range that the ball covers
        // while passing the paddle line
        double paddleLine = paddle.startPosX();
        double m = (newY - ball.startPosY()) / (newX - ball.startPosX());
        double a = m * paddleLine + ball.startPosY() - m * ball.startPosX();
        double b = Math.sqrt(Math.pow(m, 2) + 1);
        double topRange = a - ball.radius() * b;
        double bottomRange = a + ball.radius() * b;

        return (topRange > paddle.startPosY() & topRange < paddle.startPosY() + paddle.height()) ||
            (bottomRange > paddle.startPosY() & bottomRange < paddle.startPosY() + paddle.height());
    }
}
