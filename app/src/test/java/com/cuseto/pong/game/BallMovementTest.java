package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.model.Circle;
import com.cuseto.pong.model.Rectangle;

class BallMovementTest {

    @Test
    void ballMovesByVelocityTimesElapsedSeconds() {
        Circle ball = new Circle(400, 300, 8, 200, 120);
        double elapsedSeconds = 0.1;
        Rectangle dummyPaddle = new Rectangle(0,0,1,1);

        Circle moved = BallMovement.move(ball, elapsedSeconds, 0, 1000, dummyPaddle, dummyPaddle);

        assertEquals(420, moved.startPosX(), 0.000_001); // 400 + 200*0.1
        assertEquals(312, moved.startPosY(), 0.000_001); // 300 + 120*0.1
        assertEquals(8, moved.radius());
        assertEquals(200, moved.velocityX());
        assertEquals(120, moved.velocityY());
    }

    @Test
    void movementIsConsistentRegardlessOfTickSize() {
        Circle ball = new Circle(400, 300, 8, 200, 120);
        Rectangle dummyPaddle = new Rectangle(0,0,1,1);

        Circle twoHalfTicks = BallMovement.move(
            BallMovement.move(ball, 0.05, 0, 1000, dummyPaddle, dummyPaddle),
            0.05, 0, 1000, dummyPaddle, dummyPaddle
        );
        Circle oneFullTick = BallMovement.move(ball, 0.1, 0, 1000, dummyPaddle, dummyPaddle);

        assertEquals(oneFullTick.startPosX(), twoHalfTicks.startPosX(), 0.000_001);
        assertEquals(oneFullTick.startPosY(), twoHalfTicks.startPosY(), 0.000_001);
    }

    @Test
    void ballBouncesOnTopBoundary() {
        Circle ball = new Circle(400, 10, 8, 200, 120);
        Rectangle dummyPaddle = new Rectangle(0,0,1,1);
        Circle ballUpdated = BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle);

        assertEquals(400, ballUpdated.startPosX());
        assertEquals(30, ballUpdated.startPosY());
    }

    @Test
    void ballBouncesOnBottomBoundary() {
        Circle ball = new Circle(400, 110, 8, 200, 120);
        Rectangle dummyPaddle = new Rectangle(0,0,1,1);
        Circle ballUpdated = BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle);

        assertEquals(400, ballUpdated.startPosX());
        assertEquals(90, ballUpdated.startPosY());
    }

    @Test
    void ballChangesDirectionOnTopBoundary() {
        Circle ball = new Circle(400, 10, 8, 200, 120);
        Rectangle dummyPaddle = new Rectangle(0,0,1,1);
        Circle ballUpdated = BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle);

        assertEquals(-120, ballUpdated.velocityY());
    }

    @Test
    void ballChangesDirectionOnBottomBoundary() {
        Circle ball = new Circle(400, 110, 8, 200, -120);
        Rectangle dummyPaddle = new Rectangle(0,0,1,1);
        Circle ballUpdated = BallMovement.move(ball, 0.0, 20, 100, dummyPaddle, dummyPaddle);

        assertEquals(120, ballUpdated.velocityY());
    }

    @Test
    void ballBouncesOnLeftPaddel() {
        Circle ball = new Circle(15, 20, 4, -100, -120);
        Rectangle paddle = new Rectangle(10, 20, 2, 150);
        Circle ballUpdated = BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle);

        assertEquals(19, ballUpdated.startPosX());
        assertEquals(8, ballUpdated.startPosY());
    }

    @Test
    void ballChangesDirectionOnLeftPaddel() {
        Circle ball = new Circle(15, 20, 4, -100, -120);
        Rectangle paddle = new Rectangle(10, 20, 2, 150);
        Circle ballUpdated = BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle);

        assertEquals(100, ballUpdated.velocityX());
    }

    @Test
    void ballBouncesOnRightPaddel() {
        Circle ball = new Circle(5, 20, 4, 100, -120);
        Rectangle paddle = new Rectangle(10, 20, 2, 150);
        Circle ballUpdated = BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle);

        assertEquals(5, ballUpdated.startPosX());
        assertEquals(8, ballUpdated.startPosY());
    }

        @Test
    void ballChangesDirectionOnRightPaddel() {
        Circle ball = new Circle(5, 20, 4, 100, -120);
        Rectangle paddle = new Rectangle(10, 20, 2, 150);
        Circle ballUpdated = BallMovement.move(ball, 0.1, 0, 1000, paddle, paddle);

        assertEquals(-100, ballUpdated.velocityX());
    }
}
