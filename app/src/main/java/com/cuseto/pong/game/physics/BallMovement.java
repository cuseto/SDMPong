package com.cuseto.pong.game.physics;

import com.cuseto.pong.game.model.Ball;
import com.cuseto.pong.game.model.Paddle;

/**
 * Pure physics for advancing a ball's position by one frame and resolving
 * wall and paddle collisions.
 *
 * <p>Operates directly on the mutable {@link Ball} and {@link Paddle}
 * instances passed in; boundaries and paddles are supplied explicitly by
 * the caller rather than looked up from a session, so this can be exercised
 * independently of {@code GameSession}.
 */
public final class BallMovement {
    /**
     * Advances the ball's position for one frame, then resolves any
     * resulting collision with the top/bottom walls or either paddle.
     *
     * <p>A wall collision reflects the ball back inside the boundary and
     * reverses its vertical velocity. A paddle collision is detected by
     * checking whether the ball's straight-line path between its previous
     * and new position crosses the paddle's vertical span at the paddle's
     * x-position, which avoids missed collisions when the ball moves more
     * than its own size in a single frame; on collision the ball is
     * reflected back outside the paddle and its horizontal velocity is
     * reversed.
     *
     * @param ball the ball to move; its position and velocity are mutated in place
     * @param elapsedSeconds the amount of simulated time to advance, in seconds
     * @param minY the y-coordinate of the boundary the ball bounces off at the top (typically the arena's inner top boundary)
     * @param maxY the y-coordinate of the boundary the ball bounces off at the bottom (typically the arena's inner bottom boundary)
     * @param leftPaddle the left paddle, checked for a bounce as the ball crosses its x-position
     * @param rightPaddle the right paddle, checked for a bounce as the ball crosses its x-position
     */
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
