package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.model.AppConfig;


class RoundStartTest {

    @Test
    void ballMovesFromInitialPositionOncePlayStarts() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        double initialBallX = gameSession.ball.x();

        GameUpdater updater = new PaddleGameUpdater(new PaddleInputState(), gameSession)
            .andThen(new BallGameUpdater(gameSession));

        updater.update(gameSession, 0.1);

        assertNotEquals(initialBallX, gameSession.ball.x());
        assertNotEquals(0.0, gameSession.ball.velocityX());
    }
}
