package com.cuseto.pong.game.session;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.model.Arena;
import com.cuseto.pong.game.model.Ball;
import com.cuseto.pong.game.model.Paddle;

class GameSessionTest {
    @Test
    void arenaProperlyLoaded() {
        Arena arena = createSession().arena();

        assertAll(
            () -> assertEquals(1004, arena.width()),
            () -> assertEquals(708, arena.height()),
            () -> assertEquals(10, arena.anchorX()),
            () -> assertEquals(50, arena.anchorY()),
            () -> assertEquals(3, arena.boundaryThickness()),
            () -> assertEquals(998, arena.innerWidth()),
            () -> assertEquals(702, arena.innerHeight()),
            () -> assertEquals(13, arena.innerLeftBoundary()),
            () -> assertEquals(1011, arena.innerRightBoundary()),
            () -> assertEquals(53, arena.innerTopBoundary()),
            () -> assertEquals(755, arena.innerBottomBoundary())
        );
    }

    @Test
    void paddlesProperlyLoadedAndCentered() {
        GameSession gameSession = createSession();
        Paddle leftPaddle = gameSession.leftPaddle();
        Paddle rightPaddle = gameSession.rightPaddle();

        assertAll(
            () -> assertEquals(83.0, leftPaddle.x()),
            () -> assertEquals(929.0, rightPaddle.x()),
            () -> assertEquals(359.0, leftPaddle.y()),
            () -> assertEquals(359.0, rightPaddle.y()),
            () -> assertEquals(12.0, leftPaddle.width()),
            () -> assertEquals(12.0, rightPaddle.width()),
            () -> assertEquals(90.0, leftPaddle.height()),
            () -> assertEquals(90.0, rightPaddle.height()),
            () -> assertEquals(300.0, leftPaddle.speed()),
            () -> assertEquals(300.0, rightPaddle.speed())
        );
    }

    @Test
    void ballProperlyLoadedAtArenaCenter() {
        Ball ball = createSession().ball();

        assertAll(
            () -> assertEquals(512.0, ball.x()),
            () -> assertEquals(404.0, ball.y()),
            () -> assertEquals(6.0, ball.radius()),
            () -> assertEquals(120.0, ball.velocityX()),
            () -> assertEquals(80.0, ball.velocityY())
        );
    }

    @Test
    void leftPlayerIsDeclaredWinnerUponReachingFivePoints() {
        GameSession gameSession = createSession();

        for (int score = 0; score < gameSession.winningScore(); score++) {
            gameSession.incrementLeftScore();
        }

        assertEquals(gameSession.winningScore(), gameSession.leftScore());
        assertEquals(0, gameSession.rightScore());
        assertEquals(Player.LEFT, gameSession.winner());
        assertTrue(gameSession.isGameOver());
    }

    @Test
    void rightPlayerIsDeclaredWinnerUponReachingFivePoints() {
        GameSession gameSession = createSession();

        for (int score = 0; score < gameSession.winningScore(); score++) {
            gameSession.incrementRightScore();
        }

        assertEquals(0, gameSession.leftScore());
        assertEquals(gameSession.winningScore(), gameSession.rightScore());
        assertEquals(Player.RIGHT, gameSession.winner());
        assertTrue(gameSession.isGameOver());
    }

    @Test
    void startNewMatchRestoresInitialStateAfterVictory() {
        GameSession gameSession = createSession();
        double initialBallX = gameSession.ball().x();
        double initialBallY = gameSession.ball().y();
        double initialBallVelocityX = gameSession.ball().velocityX();
        double initialBallVelocityY = gameSession.ball().velocityY();
        double initialLeftPaddleY = gameSession.leftPaddle().y();
        double initialRightPaddleY = gameSession.rightPaddle().y();

        gameSession.leftPaddle().moveToY(initialLeftPaddleY + 100);
        gameSession.rightPaddle().moveToY(initialRightPaddleY - 100);
        gameSession.ball().moveTo(initialBallX + 100, initialBallY + 50);
        gameSession.ball().setVelocity(-50, 40);
        for (int score = 0; score < gameSession.winningScore(); score++) {
            gameSession.incrementLeftScore();
        }

        gameSession.startNewMatch();

        assertAll(
            () -> assertEquals(0, gameSession.leftScore()),
            () -> assertEquals(0, gameSession.rightScore()),
            () -> assertFalse(gameSession.isGameOver()),
            () -> assertNull(gameSession.winner()),
            () -> assertEquals(initialBallX, gameSession.ball().x()),
            () -> assertEquals(initialBallY, gameSession.ball().y()),
            () -> assertEquals(initialBallVelocityX, gameSession.ball().velocityX()),
            () -> assertEquals(initialBallVelocityY, gameSession.ball().velocityY()),
            () -> assertEquals(initialLeftPaddleY, gameSession.leftPaddle().y()),
            () -> assertEquals(initialRightPaddleY, gameSession.rightPaddle().y())
        );
    }

    private static GameSession createSession() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        return new GameSession(appConfig);
    }
}
