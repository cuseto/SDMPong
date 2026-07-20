package com.cuseto.pong.game;

import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;
import com.cuseto.pong.model.PaddleDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaddleGameUpdaterTest {
    
    @Test
    void updateMovesLeftPaddleUpWhenLeftDirectionIsUp() {
        GameConfig config = GameConfig.standard();
        GameState initialState = GameState.initial(config);
        PaddleInputState inputState = new PaddleInputState();
        inputState.setLeftDirection(PaddleDirection.UP);

        PaddleGameUpdater updater = new PaddleGameUpdater(inputState, config);

        GameState updatedState = updater.update(initialState, 0.1);

        assertEquals(260, updatedState.leftPaddle().startPosY(), 0.000_001); // 290 - 300*0.1
        assertEquals(initialState.rightPaddle(), updatedState.rightPaddle());
        assertEquals(initialState.ball(), updatedState.ball());
    }
}
