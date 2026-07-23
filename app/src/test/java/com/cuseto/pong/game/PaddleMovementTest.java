package com.cuseto.pong.game;

import com.cuseto.pong.model.PaddleDirection;
import com.cuseto.pong.model.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaddleMovementTest {

    @Test
    void movingUpDecreasesPaddleYBySpeedTimesElapsedSeconds() {
        Rectangle paddle = new Rectangle(124, 290, 10, 80);
        double speed = 300.0;
        double elapsedSeconds = 0.1;

        Rectangle moved = PaddleMovement.move(
            paddle,
            PaddleDirection.UP,
            elapsedSeconds,
            speed,
            84,   // arenaTop() for the standard config
            576   // arenaBottom() for the standard config
        );

        assertEquals(124, moved.startPosX());
        assertEquals(260, moved.startPosY(), 0.000_001); // 290 - 300*0.1
        assertEquals(10, moved.width());
        assertEquals(80, moved.height());
    }

    @Test
    void movingDownIncreasesPaddleYBySpeedTimesElapsedSeconds() {
        Rectangle paddle = new Rectangle(666, 290, 10, 80);
        double speed = 300.0;
        double elapsedSeconds = 0.1;

        Rectangle moved = PaddleMovement.move(
            paddle,
            PaddleDirection.DOWN,
            elapsedSeconds,
            speed,
            84,
            576
        );

        assertEquals(666, moved.startPosX());
        assertEquals(320, moved.startPosY(), 0.000_001); // 290 + 300*0.1
        assertEquals(10, moved.width());
        assertEquals(80, moved.height());
    }

    @Test
    void movingUpAtTopBoundaryStaysWithinArena() {
        Rectangle paddle = new Rectangle(124, 84, 10, 80); // already sitting at the top boundary
        double speed = 300.0;
        double elapsedSeconds = 0.5; // would overshoot by 150px if unclamped

        Rectangle moved = PaddleMovement.move(
            paddle,
            PaddleDirection.UP,
            elapsedSeconds,
            speed,
            84,
            496
        );

        assertEquals(84, moved.startPosY(), 0.000_001);
    }

    @Test
    void movingDownAtBottomBoundaryStaysWithinArena() {
        Rectangle paddle = new Rectangle(666, 496, 10, 80); // already sitting at the bottom boundary
        double speed = 300.0;
        double elapsedSeconds = 0.5; // would overshoot by 150px if unclamped

        Rectangle moved = PaddleMovement.move(
            paddle,
            PaddleDirection.DOWN,
            elapsedSeconds,
            speed,
            84,
            496
        );

        assertEquals(496, moved.startPosY(), 0.000_001);
    }
}
