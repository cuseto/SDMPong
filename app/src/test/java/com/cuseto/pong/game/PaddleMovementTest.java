package com.cuseto.pong.game;

import com.cuseto.pong.model.PaddleDirection;
import com.cuseto.pong.model.Paddle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaddleMovementTest {

    @Test
    void movingUpDecreasesPaddleYBySpeedTimesElapsedSeconds() {
        Paddle paddle = new Paddle(124, 290, 10, 80);
        double speed = 300.0;
        double elapsedSeconds = 0.1;

        Paddle moved = PaddleMovement.move(
            paddle,
            PaddleDirection.UP,
            elapsedSeconds,
            speed,
            84,   // arenaTop() for the standard config
            576   // arenaBottom() for the standard config
        );

        assertEquals(124, moved.x());
        assertEquals(260, moved.y(), 0.000_001); // 290 - 300*0.1
        assertEquals(10, moved.width());
        assertEquals(80, moved.height());
    }

    @Test
    void movingDownIncreasesPaddleYBySpeedTimesElapsedSeconds() {
        Paddle paddle = new Paddle(666, 290, 10, 80);
        double speed = 300.0;
        double elapsedSeconds = 0.1;

        Paddle moved = PaddleMovement.move(
            paddle,
            PaddleDirection.DOWN,
            elapsedSeconds,
            speed,
            84,
            576
        );

        assertEquals(666, moved.x());
        assertEquals(320, moved.y(), 0.000_001); // 290 + 300*0.1
        assertEquals(10, moved.width());
        assertEquals(80, moved.height());
    }

    @Test
    void movingUpAtTopBoundaryStaysWithinArena() {
        Paddle paddle = new Paddle(124, 84, 10, 80); // already sitting at the top boundary
        double speed = 300.0;
        double elapsedSeconds = 0.5; // would overshoot by 150px if unclamped

        Paddle moved = PaddleMovement.move(
            paddle,
            PaddleDirection.UP,
            elapsedSeconds,
            speed,
            84,
            496
        );

        assertEquals(84, moved.y(), 0.000_001);
    }

    @Test
    void movingDownAtBottomBoundaryStaysWithinArena() {
        Paddle paddle = new Paddle(666, 496, 10, 80); // already sitting at the bottom boundary
        double speed = 300.0;
        double elapsedSeconds = 0.5; // would overshoot by 150px if unclamped

        Paddle moved = PaddleMovement.move(
            paddle,
            PaddleDirection.DOWN,
            elapsedSeconds,
            speed,
            84,
            496
        );

        assertEquals(496, moved.y(), 0.000_001);
    }
}
