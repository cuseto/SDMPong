package com.cuseto.pong.game.session;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.update.ScoreGameUpdater;


public class GameSessionTestRoundReset {

    @Test
    void ballReturnsToStartingPositionAfterPoint() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        double expectedX =
            gameSession.arena().innerLeftBoundary()
                + gameSession.arena().innerWidth() / 2.0;

        double expectedY =
            gameSession.arena().innerTopBoundary()
                + gameSession.arena().innerHeight() / 2.0;

        gameSession.ball().setX(expectedX + 100);
        gameSession.ball().setY(expectedY + 50);

        gameSession.resetRound();

        assertEquals(expectedX, gameSession.ball().x());
        assertEquals(expectedY, gameSession.ball().y());
    }

    @Test
    void paddlesReturnToStartingPositionsAfterPoint() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        double expectedLeftX = gameSession.leftPaddle().x();
        double expectedLeftY = gameSession.leftPaddle().y();

        double expectedRightX = gameSession.rightPaddle().x();
        double expectedRightY = gameSession.rightPaddle().y();

        // Simulate the paddles having moved during the previous round.
        gameSession.leftPaddle().moveToY(expectedLeftY + 100);
        gameSession.rightPaddle().moveToY(expectedRightY - 100);

        gameSession.resetRound();

        assertEquals(expectedLeftX, gameSession.leftPaddle().x());
        assertEquals(expectedLeftY, gameSession.leftPaddle().y());

        assertEquals(expectedRightX, gameSession.rightPaddle().x());
        assertEquals(expectedRightY, gameSession.rightPaddle().y());
    }

    @Test
    void ballSpeedReturnsToInitialMagnitudeAfterPoint() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        double initialVelocityX = gameSession.ball().velocityX();
        double initialVelocityY = gameSession.ball().velocityY();

        // Simulate the ball having a different velocity during the previous round.
        gameSession.ball().setVelocity(
            initialVelocityX * -2,
            initialVelocityY * -2
        );

        gameSession.resetRound();

        assertEquals(Math.abs(initialVelocityX), Math.abs(gameSession.ball().velocityX()));
        assertEquals(Math.abs(initialVelocityY), Math.abs(gameSession.ball().velocityY()));
    }

    @Test
    void newRoundSamplesNewBallXDirection() {
        GameSession gameSession = new GameSession(
            loadConfig(),
            supplierReturning(true, true, false, true)
        );
        assertEquals(120.0, gameSession.ball().velocityX());

        gameSession.resetRound();

        assertEquals(-120.0, gameSession.ball().velocityX());
    }

    @Test
    void newRoundSamplesNewBallYDirection() {
        GameSession gameSession = new GameSession(
            loadConfig(),
            supplierReturning(true, true, true, false)
        );
        assertEquals(80.0, gameSession.ball().velocityY());

        gameSession.resetRound();

        assertEquals(-80.0, gameSession.ball().velocityY());
    }

    @Test
    void scoresArePreservedAfterRoundReset() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        // Simulate a score accumulated during the match.
        gameSession.incrementLeftScore();
        gameSession.incrementRightScore();
        gameSession.incrementLeftScore();

        assertEquals(2, gameSession.leftScore());
        assertEquals(1, gameSession.rightScore());

        gameSession.resetRound();

        assertEquals(2, gameSession.leftScore());
        assertEquals(1, gameSession.rightScore());
    }

    @Test
    void scoringAutomaticallyStartsNextRound() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        ScoreGameUpdater scoreGameUpdater = new ScoreGameUpdater();

        double initialBallX = gameSession.ball().x();
        double initialBallY = gameSession.ball().y();

        double rightScoringBoundary =
            gameSession.arena().innerRightBoundary();

        // Establish the previous ball position.
        scoreGameUpdater.update(gameSession, 0.0);

        // Cross the scoring boundary.
        gameSession.ball().setX(rightScoringBoundary + 1);

        scoreGameUpdater.update(gameSession, 0.0);

        assertEquals(1, gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());

        // Scoring should automatically prepare the next round.
        assertEquals(initialBallX, gameSession.ball().x());
        assertEquals(initialBallY, gameSession.ball().y());
    }

    private static BooleanSupplier supplierReturning(Boolean... values) {
        Iterator<Boolean> iterator = List.of(values).iterator();
        return iterator::next;
    }

    private static AppConfig loadConfig() {
        return ConfigLoader.load("/test-config.yaml");
    }
}
