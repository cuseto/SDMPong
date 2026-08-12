package com.cuseto.pong.game.scoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.update.ScoreGameUpdater;

class ScoringTest {

    @Test
    void leftPlayerScoresWhenBallCrossesRightScoringBoundary() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        ScoreGameUpdater scoreGameUpdater = new ScoreGameUpdater();

        double rightScoringBoundary = gameSession.arena().innerRightBoundary();

        gameSession.ball().setX(rightScoringBoundary - 1);
        scoreGameUpdater.update(gameSession, 0.0);
        gameSession.ball().setX(rightScoringBoundary + 1);

        scoreGameUpdater.update(gameSession, 0.0);

        assertEquals(1, gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
    }

    @Test
    void rightPlayerScoresWhenBallCrossesLeftScoringBoundary() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        ScoreGameUpdater scoreGameUpdater = new ScoreGameUpdater();

        double leftScoringBoundary = gameSession.arena().innerLeftBoundary();

        gameSession.ball().setX(leftScoringBoundary + 1);
        scoreGameUpdater.update(gameSession, 0.0);
        gameSession.ball().setX(leftScoringBoundary - 1);

        scoreGameUpdater.update(gameSession, 0.0);

        assertEquals(0, gameSession.leftScore());
        assertEquals(1, gameSession.rightScore());
    }

    @Test
    void sameBallCrossingDoesNotScoreMoreThanOnce() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        ScoreGameUpdater scoreGameUpdater = new ScoreGameUpdater();

        double rightScoringBoundary = gameSession.arena().innerRightBoundary();

        // Ball starts inside the playing area.
        gameSession.ball().setX(rightScoringBoundary - 1);
        scoreGameUpdater.update(gameSession, 0.0);

        // Ball crosses the right scoring boundary.
        gameSession.ball().setX(rightScoringBoundary + 1);
        scoreGameUpdater.update(gameSession, 0.0);

        // Further updates while the ball remains outside must not score again.
        scoreGameUpdater.update(gameSession, 0.0);
        scoreGameUpdater.update(gameSession, 0.0);

        assertEquals(1, gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
    }
}
