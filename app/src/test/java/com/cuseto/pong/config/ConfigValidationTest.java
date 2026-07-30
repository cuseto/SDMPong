package com.cuseto.pong.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.model.ArenaConfig;
import com.cuseto.pong.config.model.BallConfig;
import com.cuseto.pong.config.model.PaddleConfig;
import com.cuseto.pong.config.model.PaddleControlsConfig;
import com.cuseto.pong.config.model.ViewportConfig;

import javafx.scene.input.KeyCode;

class ConfigValidationTest {
    @Test
    void viewportDimensionsMustBePositive() {
        assertThrows(IllegalArgumentException.class, () -> new ViewportConfig(0, 600));
        assertThrows(IllegalArgumentException.class, () -> new ViewportConfig(800, -1));
    }

    @Test
    void arenaSpacingMustBeNonNegativeAndBoundaryMustBePositive() {
        assertThrows(IllegalArgumentException.class, () -> new ArenaConfig(-1, 20, 4));
        assertThrows(IllegalArgumentException.class, () -> new ArenaConfig(80, -1, 4));
        assertThrows(IllegalArgumentException.class, () -> new ArenaConfig(80, 20, 0));
    }

    @Test
    void ballRadiusMustBePositiveAndVelocitiesMustBeFinite() {
        assertThrows(IllegalArgumentException.class, () -> new BallConfig(0, 100.0, 100.0));
        assertThrows(IllegalArgumentException.class,
            () -> new BallConfig(8, Double.NaN, 100.0));
        assertThrows(IllegalArgumentException.class,
            () -> new BallConfig(8, 100.0, Double.POSITIVE_INFINITY));
    }

    @Test
    void paddleDimensionsAndSpeedMustBePositiveAndInsetNonNegative() {
        assertThrows(IllegalArgumentException.class, () -> new PaddleConfig(0, 80, 100, 300.0));
        assertThrows(IllegalArgumentException.class, () -> new PaddleConfig(10, 0, 100, 300.0));
        assertThrows(IllegalArgumentException.class, () -> new PaddleConfig(10, 80, -1, 300.0));
        assertThrows(IllegalArgumentException.class, () -> new PaddleConfig(10, 80, 100, 0.0));
        assertThrows(IllegalArgumentException.class,
            () -> new PaddleConfig(10, 80, 100, Double.NaN));
    }

    @Test
    void controlKeysMustBePresentAndDifferent() {
        assertThrows(NullPointerException.class,
            () -> new PaddleControlsConfig(null, KeyCode.S));
        assertThrows(NullPointerException.class,
            () -> new PaddleControlsConfig(KeyCode.W, null));
        assertThrows(IllegalArgumentException.class,
            () -> new PaddleControlsConfig(KeyCode.W, KeyCode.W));
    }
}
