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
}
