package com.cuseto.pong.game.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.model.PaddleDirection;

class PaddleInputStateTest {

    @Test
    void freshStateHasNoDirectionForEitherPaddle() {
        PaddleInputState inputState = new PaddleInputState();

        assertEquals(PaddleDirection.NONE, inputState.leftDirection());
        assertEquals(PaddleDirection.NONE, inputState.rightDirection());
    }

    @Test
    void settingLeftDirectionDoesNotAffectRightDirection() {
        PaddleInputState inputState = new PaddleInputState();

        inputState.setLeftDirection(PaddleDirection.UP);

        assertEquals(PaddleDirection.UP, inputState.leftDirection());
        assertEquals(PaddleDirection.NONE, inputState.rightDirection());
    }

    @Test
    void settingRightDirectionDoesNotAffectLeftDirection() {
        PaddleInputState inputState = new PaddleInputState();

        inputState.setRightDirection(PaddleDirection.DOWN);

        assertEquals(PaddleDirection.DOWN, inputState.rightDirection());
        assertEquals(PaddleDirection.NONE, inputState.leftDirection());
    }
}
