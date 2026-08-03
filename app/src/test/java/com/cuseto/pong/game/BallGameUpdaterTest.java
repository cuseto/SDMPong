package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.model.AppConfig;


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
}
