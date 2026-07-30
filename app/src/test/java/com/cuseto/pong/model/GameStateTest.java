package com.cuseto.pong.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {

    @Test 
    void initialPositionBallWithTopSpacing80() {
        GameConfig config = GameConfig.standard();
        GameState state = GameState.initial(config);
        
        assertEquals(400, state.ball().x());
        assertEquals(330, state.ball().y());
        assertEquals(8, state.ball().radius());
        assertEquals(config.ballVelocityX(), state.ball().velocityX());
        assertEquals(config.ballVelocityY(), state.ball().velocityY());
    }

    @Test
    void initialPositionPaddleWithTopSpacing80() {
        GameState state = GameState.initial(GameConfig.standard());

        assertEquals(124, state.leftPaddle().x());
        assertEquals(290, state.leftPaddle().y());
        assertEquals(10, state.leftPaddle().width());
        assertEquals(80, state.leftPaddle().height());
        assertEquals(666, state.rightPaddle().x());
        assertEquals(290, state.rightPaddle().y());
        assertEquals(10, state.rightPaddle().width());
        assertEquals(80, state.rightPaddle().height());
    }

    @Test
    void initialBallVelocityMatchesConfig() {
        GameConfig config = GameConfig.standard();
        GameState state = GameState.initial(GameConfig.standard());

        assertEquals(config.ballVelocityX(), state.ball().velocityX());
        assertEquals(config.ballVelocityY(), state.ball().velocityY());
    }
}
