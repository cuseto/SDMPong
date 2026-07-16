package com.cuseto.pong.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {

    @Test
    void standardConfigHasDocumentedScreenDimensions() {
        GameConfig config = GameConfig.standard();

        assertEquals(800, config.screenWidth());
        assertEquals(600, config.screenHeight());
    }

    @Test
    void standardConfigDerivesDocumentedArenaDimensions() {
        GameConfig config = GameConfig.standard();

        assertEquals(760, config.arenaWidth());
        assertEquals(500, config.arenaHeight());
    }

    @Test 
    void initialPositionBallWithTopSpacing80() {
        GameState state = GameState.initial(GameConfig.standard());
        
        assertEquals(new Circle(400, 330, 8), state.ball());
    }

    @Test
    void initialPositionPaddleWithTopSpacing80() {
        GameState state = GameState.initial(GameConfig.standard());

        assertEquals(new Rectangle(124, 290, 10, 80), state.leftPaddle());
        assertEquals(new Rectangle(666, 290, 10, 80), state.rightPaddle());
    }
}
