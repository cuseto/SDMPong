package com.cuseto.pong.game;

import com.cuseto.pong.model.PaddleDirection;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaddleKeyMappingTest {

    @Test
    void wKeyMapsToUpForLeftPaddle() {
        assertEquals(PaddleDirection.UP, PaddleKeyMapping.leftDirectionFor(KeyCode.W));
    }

    @Test
    void sKeyMapsToDownForLeftPaddle() {
        assertEquals(PaddleDirection.DOWN, PaddleKeyMapping.leftDirectionFor(KeyCode.S));
    }

    @Test
    void upKeyMapsToUpForRightPaddle() {
        assertEquals(PaddleDirection.UP, PaddleKeyMapping.rightDirectionFor(KeyCode.UP));
    }

    @Test
    void downKeyMapsToDownForRightPaddle() {
        assertEquals(PaddleDirection.DOWN, PaddleKeyMapping.rightDirectionFor(KeyCode.DOWN));
    }

    @Test
    void unrelatedKeyMapsToNoneForLeftPaddle() {
        assertEquals(PaddleDirection.NONE, PaddleKeyMapping.leftDirectionFor(KeyCode.A));
    }

    @Test
    void unrelatedKeyMapsToNoneForRightPaddle() {
        assertEquals(PaddleDirection.NONE, PaddleKeyMapping.rightDirectionFor(KeyCode.A));
    }
}
