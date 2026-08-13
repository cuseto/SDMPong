package com.cuseto.pong.game.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.session.GameSession;


class BallGameUpdaterTest {

    @Test
    void updateMovesBallByVelocityTimesElapsedSeconds() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        double initialX = gameSession.ball().x();
        double initialY = gameSession.ball().y();

        BallGameUpdater updater = new BallGameUpdater();

        updater.update(gameSession, 0.1);

        double expectedX = initialX + gameSession.ball().velocityX() * 0.1;
        double expectedY = initialY + gameSession.ball().velocityY() * 0.1;

        assertEquals(expectedX, gameSession.ball().x(), 0.000_001);
        assertEquals(expectedY, gameSession.ball().y(), 0.000_001);
    }

    @Test
    void updateDoesNotMoveBallAfterMatchEnds() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        for (int score = 0; score < gameSession.winningScore(); score++) {
            gameSession.incrementLeftScore();
        }
        gameSession.ball().moveTo(300, 250);
        gameSession.ball().setVelocity(120, 80);

        new BallGameUpdater().update(gameSession, 1.0);

        assertEquals(300, gameSession.ball().x());
        assertEquals(250, gameSession.ball().y());
    }
}
