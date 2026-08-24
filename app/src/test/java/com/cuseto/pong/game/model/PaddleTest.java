package com.cuseto.pong.game.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PaddleTest {

    @Test
    void constructorRejectsInvalidPositionDimensionsAndSpeed() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Paddle(Double.NaN, 290, 10, 80, 10)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Paddle(124, Double.POSITIVE_INFINITY, 10, 80, 10)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Paddle(124, 290, 0, 80, 10)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Paddle(124, 290, 10, -1, 10)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Paddle(124, 290, 10, 80, 0)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Paddle(124, 290, 10, 80, Double.NaN)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Paddle(124, 290, 10, 80, Double.POSITIVE_INFINITY))
        );
    }

    @Test
    void verticalPositionCanBeUpdatedWithoutChangingDimensions() {
        Paddle paddle = new Paddle(124, 290, 10, 80, 10);

        paddle.moveToY(260);

        assertEquals(124, paddle.x());
        assertEquals(260, paddle.y());
        assertEquals(10, paddle.width());
        assertEquals(80, paddle.height());
        assertEquals(10, paddle.speed());
    }

    @Test
    void invalidPositionUpdateIsRejectedWithoutChangingThePaddle() {
        Paddle paddle = new Paddle(124, 290, 10, 80, 10);

        assertThrows(IllegalArgumentException.class,
            () -> paddle.moveToY(Double.NaN));

        assertEquals(290, paddle.y());
    }
}
