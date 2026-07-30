package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;

class BallGameUpdaterTest {

    @Test
    void updateMovesBallByVelocityTimesElapsedSeconds() {
        GameConfig config = GameConfig.standard();
        GameState initialState = GameState.initial(config);
        double initialX = initialState.ball().x();
        double initialY = initialState.ball().y();

        BallGameUpdater updater = new BallGameUpdater(config);

        GameState updatedState = updater.update(initialState, 0.1);

        double expectedX = initialX + config.ballVelocityX() * 0.1;
        double expectedY = initialY + config.ballVelocityY() * 0.1;

        assertSame(initialState, updatedState);
        assertEquals(expectedX, updatedState.ball().x(), 0.000_001);
        assertEquals(expectedY, updatedState.ball().y(), 0.000_001);
        assertEquals(initialState.leftPaddle(), updatedState.leftPaddle());
        assertEquals(initialState.rightPaddle(), updatedState.rightPaddle());
    }
}
