package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;

class BallGameUpdaterTest {

    @Test
    void updateMovesBallByVelocityTimesElapsedSeconds() {
        GameConfig config = GameConfig.standard();
        GameState initialState = GameState.initial(config);

        BallGameUpdater updater = new BallGameUpdater(config);

        GameState updatedState = updater.update(initialState, 0.1);

        double expectedX = initialState.ball().x() + config.ballVelocityX() * 0.1;
        double expectedY = initialState.ball().y() + config.ballVelocityY() * 0.1;

        assertEquals(expectedX, updatedState.ball().x(), 0.000_001);
        assertEquals(expectedY, updatedState.ball().y(), 0.000_001);
        assertEquals(initialState.leftPaddle(), updatedState.leftPaddle());
        assertEquals(initialState.rightPaddle(), updatedState.rightPaddle());
    }
}
