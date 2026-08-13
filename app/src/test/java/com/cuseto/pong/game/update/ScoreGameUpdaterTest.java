package com.cuseto.pong.game.update;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;

class ScoreGameUpdaterTest {

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
}
