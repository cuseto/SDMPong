package com.cuseto.pong.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class GameConfigTest {

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
    void standardConfigExposesPlayableArenaBoundaries() {
        GameConfig config = GameConfig.standard();

        assertEquals(24, config.arenaLeft());
        assertEquals(84, config.arenaTop());
        assertEquals(776, config.arenaRight());
        assertEquals(576, config.arenaBottom());
    }

    @Test
    void standardConfigDefinesPaddleSpeed() {
        GameConfig config = GameConfig.standard();

        assertEquals(300.0, config.paddleSpeed());
    }

    @Test
    void standardConfigDefinesNonZeroHorizontalBallVelocity() {
        GameConfig config = GameConfig.standard();

        assertNotEquals(0.0, config.ballVelocityX());
    }
}
