package com.cuseto.pong.game;

import com.cuseto.pong.model.Circle;
import com.cuseto.pong.model.GameState;
import com.cuseto.pong.model.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameUpdaterTest {

    @Test
    void andThenAppliesBothUpdatersInOrder() {
        GameState state = new GameState(
            new Circle(0, 0, 8, 0, 0),
            new Rectangle(0, 0, 10, 80),
            new Rectangle(0, 0, 10, 80)
        );

        GameUpdater addTen = (s, elapsed) -> new GameState(
            new Circle(s.ball().startPosX() + 10, s.ball().startPosY(), s.ball().radius(), s.ball().velocityX(), s.ball().velocityY()),
            s.leftPaddle(),
            s.rightPaddle()
        );
        GameUpdater addHundred = (s, elapsed) -> new GameState(
            new Circle(s.ball().startPosX() + 100, s.ball().startPosY(), s.ball().radius(), s.ball().velocityX(), s.ball().velocityY()),
            s.leftPaddle(),
            s.rightPaddle()
        );

        GameUpdater combined = addTen.andThen(addHundred);

        GameState result = combined.update(state, 0.1);

        assertEquals(110, result.ball().startPosX());
    }
}
