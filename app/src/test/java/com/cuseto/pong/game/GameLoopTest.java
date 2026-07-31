package com.cuseto.pong.game;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.model.AppConfig;

class GameLoopTest {

    @Test
    void elapsedSecondsAreComputedFromAnimationTimerNanoseconds() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        List<Double> elapsedSeconds = new ArrayList<>();

        GameLoop loop = new GameLoop(
            gameSession,
            (state, elapsed) -> {
                elapsedSeconds.add(elapsed);
            },
            state -> { }
        );

        loop.tick(1_000_000_000L);
        loop.tick(1_250_000_000L);

        assertEquals(0.0, elapsedSeconds.get(0), 0.000_001);
        assertEquals(0.25, elapsedSeconds.get(1), 0.000_001);
    }
}
