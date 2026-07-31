package com.cuseto.pong.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.model.AppConfig;

class GameUpdaterTest {

    @Test
    void andThenAppliesBothUpdatersInOrder() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);

        GameUpdater setBallXToTen = (s, elapsed) ->
            s.ball.moveTo(10, s.ball.y());
        GameUpdater doubleBallX = (s, elapsed) ->
            s.ball.moveTo(s.ball.x() * 2, s.ball.y());

        GameUpdater combined = setBallXToTen.andThen(doubleBallX);

        combined.update(gameSession, 0.1);

        assertEquals(20, gameSession.ball.x());
    }
}
