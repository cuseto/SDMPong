package com.cuseto.pong.game;

import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameLoopTest {

    @Test
    void elapsedSecondsAreComputedFromAnimationTimerNanoseconds() {
        GameState initialState = GameState.initial(GameConfig.standard());
        List<Double> elapsedSeconds = new ArrayList<>();

        GameLoop loop = new GameLoop(
            initialState,
            (state, elapsed) -> {
                elapsedSeconds.add(elapsed);
                return state;
            },
            state -> { }
        );

        loop.tick(1_000_000_000L);
        loop.tick(1_250_000_000L);

        assertEquals(0.0, elapsedSeconds.get(0), 0.000_001);
        assertEquals(0.25, elapsedSeconds.get(1), 0.000_001);
    }
}
