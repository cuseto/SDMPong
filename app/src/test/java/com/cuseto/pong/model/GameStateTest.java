package com.cuseto.pong.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {

    @Test
    void standardGameStartsWithDocumentedArenaDimensions() {
        GameState state = GameState.initial(GameConfig.standard());

        assertEquals(800, state.arenaWidth());
        assertEquals(600, state.arenaHeight());
    }

    @Test 
    void initialPositionBall() {
        GameState state = GameState.initial(GameConfig.standard());
        
        assertEquals(new Circle(400, 300, 8), state.ball());
    }

    @Test
    void initialPositionPaddle () {
        GameState state = GameState.initial(GameConfig.standard());

        assertEquals(new Rectangle(100, 260, 10, 80), state.leftPaddle());
        assertEquals(new Rectangle(690, 260, 10, 80), state.rightPaddle());
    }
}
