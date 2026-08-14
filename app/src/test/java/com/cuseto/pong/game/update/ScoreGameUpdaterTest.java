package com.cuseto.pong.game.update;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;

class ScoreGameUpdaterTest {

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

        gameSession.ball().setX(rightScoringBoundary - 1);
        scoreGameUpdater.update(gameSession, 0.0);
        gameSession.ball().setX(rightScoringBoundary + 1);
        scoreGameUpdater.update(gameSession, 0.0);

        scoreGameUpdater.update(gameSession, 0.0);
        scoreGameUpdater.update(gameSession, 0.0);

        assertEquals(1, gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
    }

    @Test
    void scoreIsUpdatedWhenBallCrossesBoundaryDuringGameUpdate() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        GameUpdater updater = new BallGameUpdater().andThen(new ScoreGameUpdater());
        double rightScoringBoundary = gameSession.arena().innerRightBoundary();

        gameSession.ball().setX(rightScoringBoundary - 1);
        gameSession.ball().setVelocity(1, 0);

        updater.update(gameSession, 0.0);
        updater.update(gameSession, 2.0);

        assertEquals(1, gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
    }

    @Test
    void finalPointDeclaresWinnerAndDoesNotResetRound() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        ScoreGameUpdater scoreGameUpdater = new ScoreGameUpdater();
        double rightBoundary = gameSession.arena().innerRightBoundary();

        for (int score = 0; score < gameSession.winningScore() - 1; score++) {
            gameSession.incrementLeftScore();
        }
        gameSession.ball().setX(rightBoundary - 1);
        scoreGameUpdater.update(gameSession, 0.0);
        gameSession.ball().setX(rightBoundary + 1);

        scoreGameUpdater.update(gameSession, 0.0);

        assertEquals(gameSession.winningScore(), gameSession.leftScore());
        assertEquals(Player.LEFT, gameSession.winner());
        assertEquals(rightBoundary + 1, gameSession.ball().x());
    }

    @Test
    void updateDoesNotAwardAdditionalPointsAfterMatchEnds() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        ScoreGameUpdater scoreGameUpdater = new ScoreGameUpdater();
        double rightBoundary = gameSession.arena().innerRightBoundary();
        double leftBoundary = gameSession.arena().innerLeftBoundary();

        for (int score = 0; score < gameSession.winningScore() - 1; score++) {
            gameSession.incrementLeftScore();
        }
        gameSession.ball().setX(rightBoundary - 1);
        scoreGameUpdater.update(gameSession, 0.0);
        gameSession.ball().setX(rightBoundary + 1);
        scoreGameUpdater.update(gameSession, 0.0);

        gameSession.ball().setX(leftBoundary + 1);
        scoreGameUpdater.update(gameSession, 0.0);
        gameSession.ball().setX(leftBoundary - 1);
        scoreGameUpdater.update(gameSession, 0.0);

        assertEquals(gameSession.winningScore(), gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
        assertEquals(Player.LEFT, gameSession.winner());
    }

    @Test
    void scoringResumesAfterVictoryAndNewMatch() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        ScoreGameUpdater scoreGameUpdater = new ScoreGameUpdater();
        double rightBoundary = gameSession.arena().innerRightBoundary();

        for (int score = 0; score < gameSession.winningScore() - 1; score++) {
            gameSession.incrementLeftScore();
        }
        gameSession.ball().setX(rightBoundary - 1);
        scoreGameUpdater.update(gameSession, 0.0);
        gameSession.ball().setX(rightBoundary + 1);
        scoreGameUpdater.update(gameSession, 0.0);

        gameSession.startNewMatch();
        scoreGameUpdater.update(gameSession, 0.0);
        gameSession.ball().setX(rightBoundary + 1);
        scoreGameUpdater.update(gameSession, 0.0);

        assertEquals(1, gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
    }
}
