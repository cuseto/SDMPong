package com.cuseto.pong.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PaddleTest {

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
}
