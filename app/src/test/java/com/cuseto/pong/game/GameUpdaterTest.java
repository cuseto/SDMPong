package com.cuseto.pong.game;

import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.GameState;
import com.cuseto.pong.model.Paddle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameUpdaterTest {

    @Test
    void andThenAppliesBothUpdatersInOrder() {
        GameState state = new GameState(
            new Ball(0, 0, 8, 0, 0),
            new Paddle(0, 0, 10, 80),
            new Paddle(0, 0, 10, 80)
        );

        GameUpdater setBallXToTen = (s, elapsed) ->
            s.ball().moveTo(10, s.ball().y());
        GameUpdater doubleBallX = (s, elapsed) ->
            s.ball().moveTo(s.ball().x() * 2, s.ball().y());

        GameUpdater combined = setBallXToTen.andThen(doubleBallX);

        combined.update(state, 0.1);

        assertEquals(20, state.ball().x());
    }
}
