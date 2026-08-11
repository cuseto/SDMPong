package com.cuseto.pong.game.scoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.session.GameSession;

class ScoringTest {

    @Test
    void leftPlayerScoresWhenBallCrossesRightScoringBoundary() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        double rightScoringBoundary = gameSession.arena().innerRightBoundary();

        gameSession.ball().setX(rightScoringBoundary + 1);

        gameSession.updateScore();

        assertEquals(1, gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
    }

    @Test
    void rightPlayerScoresWhenBallCrossesLeftScoringBoundary() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        double leftScoringBoundary = gameSession.arena().innerLeftBoundary();

        gameSession.ball().setX(leftScoringBoundary - 1);

        gameSession.updateScore();

        assertEquals(0, gameSession.leftScore());
        assertEquals(1, gameSession.rightScore());
    }
}
