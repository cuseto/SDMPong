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
        double initialX = initialState.ball().x();
        double initialY = initialState.ball().y();

        BallGameUpdater updater = new BallGameUpdater(config);

        updater.update(initialState, 0.1);

        double expectedX = initialX + config.ballVelocityX() * 0.1;
        double expectedY = initialY + config.ballVelocityY() * 0.1;

        assertEquals(expectedX, initialState.ball().x(), 0.000_001);
        assertEquals(expectedY, initialState.ball().y(), 0.000_001);
    }
}
