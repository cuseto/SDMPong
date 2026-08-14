package com.cuseto.pong.game.physics;

import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.Paddle;

public final class BallMovement {
    public static void move(
        Ball ball,
        double elapsedSeconds,
        int minY,
        int maxY,
        Paddle leftPaddle,
        Paddle rightPaddle
    ) {
        double previousX = ball.x();
        double previousY = ball.y();

        moveBall(ball, elapsedSeconds);
        bounceOffHorizontalWalls(ball, minY, maxY);
        bounceOffPaddles(ball, leftPaddle, rightPaddle, previousX, previousY);
    }

    private static void moveBall(Ball ball, double elapsedSeconds) {
        ball.setX(ball.x() + ball.velocityX() * elapsedSeconds);
        ball.setY(ball.y() + ball.velocityY() * elapsedSeconds);
    }

    private static void bounceOffHorizontalWalls(Ball ball, int minY, int maxY) {
        double ballTopY = ball.y() - ball.radius();
        double ballBottomY = ball.y() + ball.radius();
        boolean touchedTopBoundary = ballTopY <= minY;
        boolean touchedBottomBoundary = ballBottomY >= maxY;

        if (touchedTopBoundary || touchedBottomBoundary) {
            if (touchedTopBoundary) ball.setY(ball.y() + 2*(minY - ballTopY));
            if (touchedBottomBoundary) ball.setY(ball.y() - 2*(ballBottomY - maxY));
            ball.setVelocity(ball.velocityX(), -1 * ball.velocityY());
        }
    }

    private static void bounceOffPaddles(
        Ball ball,
        Paddle leftPaddle,
        Paddle rightPaddle,
        double previousX,
        double previousY
    ) {
        double ballX = ball.x();
        double minX = leftPaddle.x() + leftPaddle.width();
        double maxX = rightPaddle.x();

        // left paddle
        if (ballX <= minX && previousX > minX &&
            ballCrossedPaddle(ball, leftPaddle, previousX, previousY)) {
            ball.setX(minX + (minX - ballX));
            ball.setVelocity(-1 * ball.velocityX(), ball.velocityY());
        }

        // right paddle
        if (previousX < maxX && ballX >= maxX &&
            ballCrossedPaddle(ball, rightPaddle, previousX, previousY)) {
            ball.setX(maxX - (ballX - maxX));
            ball.setVelocity(-1 * ball.velocityX(), ball.velocityY());
        }
    }

    private static boolean ballCrossedPaddle(
        Ball ball,
        Paddle paddle,
        double previousX,
        double previousY
    ) {
        // take the Y range that the ball covers
        // while passing the paddle line
        double paddleLine = paddle.x();
        double m = (ball.y() - previousY) / (ball.x() - previousX);
        double a = m * paddleLine + previousY - m * previousX;
        double b = Math.sqrt(Math.pow(m, 2) + 1);
        double topRange = a - ball.radius() * b;
        double bottomRange = a + ball.radius() * b;

        return (topRange > paddle.y() && topRange < paddle.y() + paddle.height()) ||
            (bottomRange > paddle.y() && bottomRange < paddle.y() + paddle.height());
    }
}
