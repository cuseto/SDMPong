package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.model.AppConfig;
import com.cuseto.pong.model.PaddleDirection;

public class PaddleGameUpdaterTest {
    
    @Test
    void updateMovesLeftPaddleUpWhenLeftDirectionIsUp() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        double initialBallX = gameSession.ball().x();
        double initialBallY = gameSession.ball().y();
        double initialRightPaddleY = gameSession.rightPaddle.y();
        PaddleInputState inputState = new PaddleInputState();
        inputState.setLeftDirection(PaddleDirection.UP);

        PaddleGameUpdater updater = new PaddleGameUpdater(inputState, gameSession);

        updater.update(gameSession, 0.1);

        assertEquals(329, gameSession.leftPaddle.y(), 0.000_001); // 359 - 300*0.1
        assertEquals(initialRightPaddleY, gameSession.rightPaddle.y());
        assertEquals(initialBallX, gameSession.ball().x());
        assertEquals(initialBallY, gameSession.ball().y());
    }
}
