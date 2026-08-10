package com.cuseto.pong.game.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.model.Paddle;
import com.cuseto.pong.model.PaddleDirection;

class PaddleMovementTest {

    @Test
    void movingUpDecreasesPaddleYBySpeedTimesElapsedSeconds() {
        double speed = 300.0;
        Paddle paddle = new Paddle(124, 290, 10, 80, speed);
        double elapsedSeconds = 0.1;

        PaddleMovement.move(
            paddle,
            PaddleDirection.UP,
            elapsedSeconds,
            84,   // arenaTop() for the standard config
            576   // arenaBottom() for the standard config
        );

        assertEquals(124, paddle.x());
        assertEquals(260, paddle.y(), 0.000_001); // 290 - 300*0.1
        assertEquals(10, paddle.width());
        assertEquals(80, paddle.height());
    }

    @Test
    void movingDownIncreasesPaddleYBySpeedTimesElapsedSeconds() {
        double speed = 300.0;
        Paddle paddle = new Paddle(666, 290, 10, 80, speed);
        double elapsedSeconds = 0.1;

        PaddleMovement.move(
            paddle,
            PaddleDirection.DOWN,
            elapsedSeconds,
            84,
            576
        );

        assertEquals(666, paddle.x());
        assertEquals(320, paddle.y(), 0.000_001); // 290 + 300*0.1
        assertEquals(10, paddle.width());
        assertEquals(80, paddle.height());
    }

    @Test
    void movingUpAtTopBoundaryStaysWithinArena() {
        double speed = 300.0;
        Paddle paddle = new Paddle(124, 84, 10, 80, speed); // already sitting at the top boundary
        double elapsedSeconds = 0.5; // would overshoot by 150px if unclamped

        PaddleMovement.move(
            paddle,
            PaddleDirection.UP,
            elapsedSeconds,
            84,
            496
        );

        assertEquals(84, paddle.y(), 0.000_001);
    }

    @Test
    void movingDownAtBottomBoundaryStaysWithinArena() {
        double speed = 300.0;
        Paddle paddle = new Paddle(666, 496, 10, 80, speed); // already sitting at the bottom boundary
        double elapsedSeconds = 0.5; // would overshoot by 150px if unclamped

        PaddleMovement.move(
            paddle,
            PaddleDirection.DOWN,
            elapsedSeconds,
            84,
            496
        );

        assertEquals(496, paddle.y(), 0.000_001);
    }
}
