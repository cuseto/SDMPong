package com.cuseto.pong.game.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.game.model.Ball;
import com.cuseto.pong.game.model.Paddle;

class BallMovementTest {

    @Test
    void ballMovesByVelocityTimesElapsedSeconds() {
        Ball ball = new Ball(400, 300, 8, 200, 120);
        double elapsedSeconds = 0.1;
        Paddle dummyPaddle = new Paddle(0,0,1,1,1);

        BallMovement.move(ball, elapsedSeconds, 0, 1000, dummyPaddle, dummyPaddle, false);

        assertEquals(420, ball.x(), 0.000_001); // 400 + 200*0.1
        assertEquals(312, ball.y(), 0.000_001); // 300 + 120*0.1
        assertEquals(8, ball.radius());
        assertEquals(200, ball.velocityX());
        assertEquals(120, ball.velocityY());
    }

    @Test
    void movementIsConsistentRegardlessOfTickSize() {
        Ball twoHalfTicks = new Ball(400, 300, 8, 200, 120);
        Ball oneFullTick = new Ball(400, 300, 8, 200, 120);
        Paddle dummyPaddle = new Paddle(0,0,1,1,1);

        BallMovement.move(twoHalfTicks, 0.05, 0, 1000, dummyPaddle, dummyPaddle, false);
        BallMovement.move(twoHalfTicks, 0.05, 0, 1000, dummyPaddle, dummyPaddle, false);
        BallMovement.move(oneFullTick, 0.1, 0, 1000, dummyPaddle, dummyPaddle, false);

        assertEquals(oneFullTick.x(), twoHalfTicks.x(), 0.000_001);
        assertEquals(oneFullTick.y(), twoHalfTicks.y(), 0.000_001);
    }

    @Test
    void ballBouncesOnTopBoundary() {
        Ball ball = new Ball(400, 22, 8, 200, 120);
        Paddle dummyPaddle = new Paddle(0,0,1,1,1);
        BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle, false);

        assertEquals(400, ball.x());
        assertEquals(34, ball.y());
    }

    @Test
    void ballBouncesOnBottomBoundary() {
        Ball ball = new Ball(400, 120, 8, 200, 120);
        Paddle dummyPaddle = new Paddle(0,0,1,1,1);
        BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle, false);

        assertEquals(400, ball.x());
        assertEquals(64, ball.y());
    }

    @Test
    void ballChangesDirectionOnTopBoundary() {
        Ball ball = new Ball(400, 10, 8, 200, 120);
        Paddle dummyPaddle = new Paddle(0,0,1,1,1);
        BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle, false);

        assertEquals(-120, ball.velocityY());
    }

    @Test
    void ballChangesDirectionOnBottomBoundary() {
        Ball ball = new Ball(400, 110, 8, 200, -120);
        Paddle dummyPaddle = new Paddle(0,0,1,1,1);
        BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle, false);

        assertEquals(120, ball.velocityY());
    }

    @Test
    void ballBouncesOnLeftPaddel() {
        Ball ball = new Ball(15, 20, 4, -100, -120);
        Paddle paddle = new Paddle(10, 20, 2, 150, 1);
        BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle, false);

        assertEquals(19, ball.x());
        assertEquals(8, ball.y());
    }

    @Test
    void ballOnlyChangesXDirectionAfterBouncingOnLeftPaddleWhenSpeedIncreaseDisabled() {
        Ball ball = new Ball(15, 20, 4, -100, -120);
        Paddle paddleLeft = new Paddle(10, 20, 2, 150, 1);
        Paddle paddleRight = new Paddle(10, 20, 2, 150, 1);
        BallMovement.move(ball, 0.1, 0, 1000, paddleLeft, paddleRight, false);

        assertEquals(100, ball.velocityX());
    }

    @Test
    void ballIncreasesXSpeedAfterBouncingOnLeftPaddleWhenEnabled() {
        Ball ball = new Ball(15, 20, 4, -100, -120);
        Paddle leftPaddle = new Paddle(10, 20, 2, 150, 1);
        Paddle rightPaddle = new Paddle(1000, 20, 2, 150, 1);

        BallMovement.move(ball, 0.1, 0, 1000, leftPaddle, rightPaddle, true);

        assertEquals(110, ball.velocityX(), 0.000_001);
    }

    @Test
    void ballBouncesOnRightPaddel() {
        Ball ball = new Ball(5, 20, 4, 100, -120);
        Paddle paddle = new Paddle(10, 20, 2, 150, 1);
        BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle, false);

        assertEquals(5, ball.x());
        assertEquals(8, ball.y());
    }

    @Test
    void ballOnlyChangesXDirectionAfterBouncingOnRightPaddleWhenSpeedIncreaseDisabled() {
        Ball ball = new Ball(5, 20, 4, 100, -120);
        Paddle paddle = new Paddle(10, 20, 2, 150, 1);
        BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle, false);

        assertEquals(-100, ball.velocityX());
    }

    @Test
    void ballIncreasesXSpeedAfterBouncingOnRightPaddleWhenEnabled() {
        Ball ball = new Ball(5, 20, 4, 100, -120);
        Paddle leftPaddle = new Paddle(-1000, 20, 2, 150, 1);
        Paddle rightPaddle = new Paddle(10, 20, 2, 150, 1);

        BallMovement.move(ball, 0.1, 0, 1000, leftPaddle, rightPaddle, true);

        assertEquals(-110, ball.velocityX(), 0.000_001);
    }
}
