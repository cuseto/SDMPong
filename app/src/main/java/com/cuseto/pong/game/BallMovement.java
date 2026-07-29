package com.cuseto.pong.game;

import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.Paddle;

public final class BallMovement {
    private BallMovement() {
    }

    public static Ball move(
        Ball ball,
        double elapsedSeconds,
        int minY,
        int maxY,
        Paddle leftPaddle,
        Paddle rightPaddle
    ) {
        double velocityY = ball.velocityY();
        double velocityX = ball.velocityX();
        double minX = leftPaddle.x() + leftPaddle.width();
        double maxX = rightPaddle.x();

        // basic ball update
        double newX = ball.x() + ball.velocityX() * elapsedSeconds;
        double newY = ball.y() + ball.velocityY() * elapsedSeconds;

        // checking arena bounce
        if (newY <= minY || newY >= maxY) {
            if (newY <= minY) newY = minY + (minY - newY);
            if (newY >= maxY) newY = maxY - (newY - maxY);
            velocityY *= -1;
        }

        // // checking paddles bounce
        // left paddle
        if (newX <= minX & ball.x() > minX) {
            if (ballCrossedPaddle(ball, leftPaddle, newX, newY)) {
                newX = minX + (minX - newX);
                velocityX *= -1;
            }
        }

        // right paddle
        if (ball.x() < maxX & newX >= maxX) {
            if (ballCrossedPaddle(ball, rightPaddle, newX, newY)) {
                newX = maxX - (newX - maxX);
                velocityX *= -1;
            }
        }

        return new Ball(newX, newY, ball.radius(), velocityX, velocityY);
    }

    private static boolean ballCrossedPaddle(Ball ball, Paddle paddle, double newX, double newY) {
        // take the Y range that the ball covers
        // while passing the paddle line
        double paddleLine = paddle.x();
        double m = (newY - ball.y()) / (newX - ball.x());
        double a = m * paddleLine + ball.y() - m * ball.x();
        double b = Math.sqrt(Math.pow(m, 2) + 1);
        double topRange = a - ball.radius() * b;
        double bottomRange = a + ball.radius() * b;

        return (topRange > paddle.y() & topRange < paddle.y() + paddle.height()) ||
            (bottomRange > paddle.y() & bottomRange < paddle.y() + paddle.height());
    }
}
