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

        double expectedX = initialState.ball().startPosX() + config.ballVelocityX() * 0.1;
        double expectedY = initialState.ball().startPosY() + config.ballVelocityY() * 0.1;

        assertEquals(expectedX, updatedState.ball().startPosX(), 0.000_001);
        assertEquals(expectedY, updatedState.ball().startPosY(), 0.000_001);
        assertEquals(initialState.leftPaddle(), updatedState.leftPaddle());
        assertEquals(initialState.rightPaddle(), updatedState.rightPaddle());
    }
}
