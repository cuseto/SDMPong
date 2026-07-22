package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.model.Circle;

class BallMovementTest {

    @Test
    void ballMovesByVelocityTimesElapsedSeconds() {
        Circle ball = new Circle(400, 300, 8, 200, 120);
        double elapsedSeconds = 0.1;

        Circle moved = BallMovement.move(ball, elapsedSeconds, 0, 1000);

        assertEquals(420, moved.startPosX(), 0.000_001); // 400 + 200*0.1
        assertEquals(312, moved.startPosY(), 0.000_001); // 300 + 120*0.1
        assertEquals(8, moved.radius());
        assertEquals(200, moved.velocityX());
        assertEquals(120, moved.velocityY());
    }

    @Test
    void movementIsConsistentRegardlessOfTickSize() {
        Circle ball = new Circle(400, 300, 8, 200, 120);

        Circle twoHalfTicks = BallMovement.move(BallMovement.move(ball, 0.05, 0, 1000), 0.05, 0, 1000);
        Circle oneFullTick = BallMovement.move(ball, 0.1, 0, 1000);

        assertEquals(oneFullTick.startPosX(), twoHalfTicks.startPosX(), 0.000_001);
        assertEquals(oneFullTick.startPosY(), twoHalfTicks.startPosY(), 0.000_001);
    }

    @Test
    void ballBouncesOnTopBoundary() {
        Circle ball = new Circle(400, 10, 8, 200, 120);
        Circle ballUpdated = BallMovement.move(ball, 0.0, 20, 100);

        assertEquals(400, ballUpdated.startPosX());
        assertEquals(30, ballUpdated.startPosY());
    }

    @Test
    void ballBouncesOnBottomBoundary() {
        Circle ball = new Circle(400, 110, 8, 200, 120);
        Circle ballUpdated = BallMovement.move(ball, 0.0, 20, 100);

        assertEquals(400, ballUpdated.startPosX());
        assertEquals(90, ballUpdated.startPosY());
    }

    @Test
    void ballChangesDirectionOnTopBoundary() {
        Circle ball = new Circle(400, 10, 8, 200, 120);
        Circle ballUpdated = BallMovement.move(ball, 0.0, 20, 100);

        assertEquals(-120, ballUpdated.velocityY());
    }

    @Test
    void ballChangesDirectionOnBottomBoundary() {
        Circle ball = new Circle(400, 110, 8, 200, -120);
        Circle ballUpdated = BallMovement.move(ball, 0.0, 20, 100);

        assertEquals(120, ballUpdated.velocityY());
    }
}
