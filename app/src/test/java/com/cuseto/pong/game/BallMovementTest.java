package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.Paddle;

class BallMovementTest {

    @Test
    void ballMovesByVelocityTimesElapsedSeconds() {
        Ball ball = new Ball(400, 300, 8, 200, 120);
        double elapsedSeconds = 0.1;
        Paddle dummyPaddle = new Paddle(0,0,1,1);

        Ball moved = BallMovement.move(ball, elapsedSeconds, 0, 1000, dummyPaddle, dummyPaddle);

        assertEquals(420, moved.startPosX(), 0.000_001); // 400 + 200*0.1
        assertEquals(312, moved.startPosY(), 0.000_001); // 300 + 120*0.1
        assertEquals(8, moved.radius());
        assertEquals(200, moved.velocityX());
        assertEquals(120, moved.velocityY());
    }

    @Test
    void movementIsConsistentRegardlessOfTickSize() {
        Ball ball = new Ball(400, 300, 8, 200, 120);
        Paddle dummyPaddle = new Paddle(0,0,1,1);

        Ball twoHalfTicks = BallMovement.move(
            BallMovement.move(ball, 0.05, 0, 1000, dummyPaddle, dummyPaddle),
            0.05, 0, 1000, dummyPaddle, dummyPaddle
        );
        Ball oneFullTick = BallMovement.move(ball, 0.1, 0, 1000, dummyPaddle, dummyPaddle);

        assertEquals(oneFullTick.startPosX(), twoHalfTicks.startPosX(), 0.000_001);
        assertEquals(oneFullTick.startPosY(), twoHalfTicks.startPosY(), 0.000_001);
    }

    @Test
    void ballBouncesOnTopBoundary() {
        Ball ball = new Ball(400, 10, 8, 200, 120);
        Paddle dummyPaddle = new Paddle(0,0,1,1);
        Ball ballUpdated = BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle);

        assertEquals(400, ballUpdated.startPosX());
        assertEquals(30, ballUpdated.startPosY());
    }

    @Test
    void ballBouncesOnBottomBoundary() {
        Ball ball = new Ball(400, 110, 8, 200, 120);
        Paddle dummyPaddle = new Paddle(0,0,1,1);
        Ball ballUpdated = BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle);

        assertEquals(400, ballUpdated.startPosX());
        assertEquals(90, ballUpdated.startPosY());
    }

    @Test
    void ballChangesDirectionOnTopBoundary() {
        Ball ball = new Ball(400, 10, 8, 200, 120);
        Paddle dummyPaddle = new Paddle(0,0,1,1);
        Ball ballUpdated = BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle);

        assertEquals(-120, ballUpdated.velocityY());
    }

    @Test
    void ballChangesDirectionOnBottomBoundary() {
        Ball ball = new Ball(400, 110, 8, 200, -120);
        Paddle dummyPaddle = new Paddle(0,0,1,1);
        Ball ballUpdated = BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle);

        assertEquals(120, ballUpdated.velocityY());
    }

    @Test
    void ballBouncesOnLeftPaddel() {
        Ball ball = new Ball(15, 20, 4, -100, -120);
        Paddle paddle = new Paddle(10, 20, 2, 150);
        Ball ballUpdated = BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle);

        assertEquals(19, ballUpdated.startPosX());
        assertEquals(8, ballUpdated.startPosY());
    }

    @Test
    void ballChangesDirectionOnLeftPaddel() {
        Ball ball = new Ball(15, 20, 4, -100, -120);
        Paddle paddle = new Paddle(10, 20, 2, 150);
        Ball ballUpdated = BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle);

        assertEquals(100, ballUpdated.velocityX());
    }

    @Test
    void ballBouncesOnRightPaddel() {
        Ball ball = new Ball(5, 20, 4, 100, -120);
        Paddle paddle = new Paddle(10, 20, 2, 150);
        Ball ballUpdated = BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle);

        assertEquals(5, ballUpdated.startPosX());
        assertEquals(8, ballUpdated.startPosY());
    }

        @Test
    void ballChangesDirectionOnRightPaddel() {
        Ball ball = new Ball(5, 20, 4, 100, -120);
        Paddle paddle = new Paddle(10, 20, 2, 150);
        Ball ballUpdated = BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle);

        assertEquals(-100, ballUpdated.velocityX());
    }
}
