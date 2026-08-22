package com.cuseto.pong.game.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.input.PaddleInputState;
import com.cuseto.pong.game.model.PaddleDirection;
import com.cuseto.pong.game.session.GameSession;

public class PaddleGameUpdaterTest {
    
    @Test
    void updateMovesLeftPaddleUpWhenLeftDirectionIsUp() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        double initialBallX = gameSession.ball().x();
        double initialBallY = gameSession.ball().y();
        double initialRightPaddleY = gameSession.rightPaddle().y();
        PaddleInputState inputState = new PaddleInputState();
        inputState.setLeftDirection(PaddleDirection.UP);

        PaddleGameUpdater updater = new PaddleGameUpdater(inputState);

        updater.update(gameSession, 0.1);

        assertEquals(329, gameSession.leftPaddle().y(), 0.000_001); // 359 - 300*0.1
        assertEquals(initialRightPaddleY, gameSession.rightPaddle().y());
        assertEquals(initialBallX, gameSession.ball().x());
        assertEquals(initialBallY, gameSession.ball().y());
    }

    @Test
    void updateDoesNotMovePaddlesAfterMatchEnds() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        for (int score = 0; score < gameSession.winningScore(); score++) {
            gameSession.incrementLeftScore();
        }
        gameSession.leftPaddle().moveToY(200);
        gameSession.rightPaddle().moveToY(300);

        PaddleInputState inputState = new PaddleInputState();
        inputState.setLeftDirection(PaddleDirection.DOWN);
        inputState.setRightDirection(PaddleDirection.UP);

        new PaddleGameUpdater(inputState).update(gameSession, 1.0);

        assertEquals(200, gameSession.leftPaddle().y());
        assertEquals(300, gameSession.rightPaddle().y());
    }
}
