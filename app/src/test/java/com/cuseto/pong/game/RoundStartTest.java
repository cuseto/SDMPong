package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;

class RoundStartTest {

    @Test
    void ballMovesFromInitialPositionOncePlayStarts() {
        GameConfig config = GameConfig.standard();
        GameState readyState = GameState.initial(config);
        double initialBallX = readyState.ball().x();

        GameUpdater updater = new PaddleGameUpdater(new PaddleInputState(), config)
            .andThen(new BallGameUpdater(config));

        updater.update(readyState, 0.1);

        assertNotEquals(initialBallX, readyState.ball().x());
        assertNotEquals(0.0, readyState.ball().velocityX());
    }
}
