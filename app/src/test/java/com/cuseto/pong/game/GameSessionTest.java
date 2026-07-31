package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.model.AppConfig;
import com.cuseto.pong.model.Arena;
import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.Paddle;

class GameSessionTest {

    @Test
    void arenaProperlyLoaded() {
        Arena arena = createSession().arena;

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
        Paddle leftPaddle = gameSession.leftPaddle;
        Paddle rightPaddle = gameSession.rightPaddle;

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
        Ball ball = createSession().ball;

        assertAll(
            () -> assertEquals(512.0, ball.x()),
            () -> assertEquals(404.0, ball.y()),
            () -> assertEquals(6.0, ball.radius()),
            () -> assertEquals(120.0, ball.velocityX()),
            () -> assertEquals(80.0, ball.velocityY())
        );
    }

    private static GameSession createSession() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        return new GameSession(appConfig);
    }
}
