package com.cuseto.pong.game;

import com.cuseto.pong.model.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BallMovementTest {

    @Test
    void ballMovesByVelocityTimesElapsedSeconds() {
        Circle ball = new Circle(400, 300, 8, 200, 120);
        double elapsedSeconds = 0.1;

        Circle moved = BallMovement.move(ball, elapsedSeconds);

        assertEquals(420, moved.startPosX(), 0.000_001); // 400 + 200*0.1
        assertEquals(312, moved.startPosY(), 0.000_001); // 300 + 120*0.1
        assertEquals(8, moved.radius());
        assertEquals(200, moved.velocityX());
        assertEquals(120, moved.velocityY());
    }

    @Test
    void movementIsConsistentRegardlessOfTickSize() {
        Circle ball = new Circle(400, 300, 8, 200, 120);

        Circle twoHalfTicks = BallMovement.move(BallMovement.move(ball, 0.05), 0.05);
        Circle oneFullTick = BallMovement.move(ball, 0.1);

        assertEquals(oneFullTick.startPosX(), twoHalfTicks.startPosX(), 0.000_001);
        assertEquals(oneFullTick.startPosY(), twoHalfTicks.startPosY(), 0.000_001);
    }
}
