package com.cuseto.pong.game;

import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoundStartTest {

    @Test
    void ballMovesFromInitialPositionOncePlayStarts() {
        GameConfig config = GameConfig.standard();
        GameState readyState = GameState.initial(config);

        GameUpdater updater = new PaddleGameUpdater(new PaddleInputState(), config)
            .andThen(new BallGameUpdater());

        GameState afterOneTick = updater.update(readyState, 0.1);

        assertNotEquals(readyState.ball().startPosX(), afterOneTick.ball().startPosX());
        assertNotEquals(0.0, readyState.ball().velocityX());
    }
}
