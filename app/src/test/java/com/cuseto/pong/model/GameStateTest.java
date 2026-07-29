package com.cuseto.pong.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {

    @Test 
    void initialPositionBallWithTopSpacing80() {
        GameConfig config = GameConfig.standard();
        GameState state = GameState.initial(config);
        
        assertEquals(new Ball(400, 330, 8, config.ballVelocityX(), config.ballVelocityY()), state.ball());
    }

    @Test
    void initialPositionPaddleWithTopSpacing80() {
        GameState state = GameState.initial(GameConfig.standard());

        assertEquals(new Paddle(124, 290, 10, 80), state.leftPaddle());
        assertEquals(new Paddle(666, 290, 10, 80), state.rightPaddle());
    }

    @Test
    void initialBallVelocityMatchesConfig() {
        GameConfig config = GameConfig.standard();
        GameState state = GameState.initial(GameConfig.standard());

        assertEquals(config.ballVelocityX(), state.ball().velocityX());
        assertEquals(config.ballVelocityY(), state.ball().velocityY());
    }
}
