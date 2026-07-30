package com.cuseto.pong.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BallTest {

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
}
