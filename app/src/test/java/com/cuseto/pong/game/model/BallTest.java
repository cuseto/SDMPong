package com.cuseto.pong.game.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BallTest {

    @Test
    void constructorRejectsNonFinitePositionAndVelocity() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Ball(Double.NaN, 50, 8, 200, 120)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Ball(100, Double.POSITIVE_INFINITY, 8, 200, 120)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Ball(100, 50, 8, Double.NEGATIVE_INFINITY, 120)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Ball(100, 50, 8, 200, Double.NaN))
        );
    }

    @Test
    void constructorRejectsNonPositiveOrNonFiniteRadius() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Ball(100, 50, 0, 200, 120)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Ball(100, 50, -1, 200, 120)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Ball(100, 50, Double.POSITIVE_INFINITY, 200, 120))
        );
    }

    @Test
    void positionAndVelocityCanBeUpdated() {
        Ball ball = new Ball(100, 50, 8, 200, 120);

        ball.moveTo(120, 62);
        ball.setVelocity(-200, -120);

        assertEquals(120, ball.x());
        assertEquals(62, ball.y());
        assertEquals(-200, ball.velocityX());
        assertEquals(-120, ball.velocityY());
    }

    @Test
    void invalidPositionUpdateIsRejectedWithoutChangingTheBall() {
        Ball ball = new Ball(100, 50, 8, 200, 120);

        assertThrows(IllegalArgumentException.class,
            () -> ball.moveTo(Double.NaN, Double.POSITIVE_INFINITY));

        assertEquals(100, ball.x());
        assertEquals(50, ball.y());
    }

    @Test
    void invalidVelocityUpdateIsRejectedWithoutChangingTheBall() {
        Ball ball = new Ball(100, 50, 8, 200, 120);

        assertThrows(IllegalArgumentException.class,
            () -> ball.setVelocity(Double.NaN, Double.NEGATIVE_INFINITY));

        assertEquals(200, ball.velocityX());
        assertEquals(120, ball.velocityY());
    }
}
