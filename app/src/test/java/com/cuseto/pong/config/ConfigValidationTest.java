package com.cuseto.pong.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.cuseto.pong.config.schema.ViewportConfig;
import com.cuseto.pong.config.schema.controls.PaddleControlsConfig;
import com.cuseto.pong.config.schema.game.ArenaConfig;
import com.cuseto.pong.config.schema.game.BallConfig;
import com.cuseto.pong.config.schema.game.GamePageConfig;
import com.cuseto.pong.config.schema.game.PaddleConfig;

import javafx.scene.input.KeyCode;

class ConfigValidationTest {
    @Test
    void viewportDimensionsMustBePositive() {
        assertInvalid(IllegalArgumentException.class, "screenWidth",
            () -> new ViewportConfig(0, 600));
        assertInvalid(IllegalArgumentException.class, "screenHeight",
            () -> new ViewportConfig(800, -1));
    }

    @Test
    void arenaSpacingMustBeNonNegativeAndBoundaryMustBePositive() {
        assertInvalid(IllegalArgumentException.class, "spacingTop",
            () -> new ArenaConfig(-1, 20, 4));
        assertInvalid(IllegalArgumentException.class, "spacingOther",
            () -> new ArenaConfig(80, -1, 4));
        assertInvalid(IllegalArgumentException.class, "boundaryThickness",
            () -> new ArenaConfig(80, 20, 0));
    }

    @Test
    void ballRadiusMustBePositiveAndVelocitiesMustBeFinite() {
        assertInvalid(IllegalArgumentException.class, "radius",
            () -> new BallConfig(0, 100.0, 100.0));
        assertInvalid(IllegalArgumentException.class, "initialVelocityX",
            () -> new BallConfig(8, Double.NaN, 100.0));
        assertInvalid(IllegalArgumentException.class, "initialVelocityY",
            () -> new BallConfig(8, 100.0, Double.POSITIVE_INFINITY));
    }

    @Test
    void paddleDimensionsAndSpeedMustBePositiveAndInsetNonNegative() {
        assertInvalid(IllegalArgumentException.class, "width",
            () -> new PaddleConfig(0, 80, 100, 300.0));
        assertInvalid(IllegalArgumentException.class, "height",
            () -> new PaddleConfig(10, 0, 100, 300.0));
        assertInvalid(IllegalArgumentException.class, "inset",
            () -> new PaddleConfig(10, 80, -1, 300.0));
        assertInvalid(IllegalArgumentException.class, "speed",
            () -> new PaddleConfig(10, 80, 100, 0.0));
        assertInvalid(IllegalArgumentException.class, "speed",
            () -> new PaddleConfig(10, 80, 100, Double.NaN));
    }

    @Test
    void winningScoreMustBePositive() {
        ArenaConfig arena = new ArenaConfig(80, 20, 4);
        BallConfig ball = new BallConfig(8, 100.0, 100.0);
        PaddleConfig paddle = new PaddleConfig(10, 80, 100, 300.0);

        assertInvalid(IllegalArgumentException.class, "winningScore",
            () -> new GamePageConfig(arena, ball, paddle, 0));
    }

    @Test
    void controlKeysMustBePresentAndDifferent() {
        assertInvalid(NullPointerException.class, "up",
            () -> new PaddleControlsConfig(null, KeyCode.S));
        assertInvalid(NullPointerException.class, "down",
            () -> new PaddleControlsConfig(KeyCode.W, null));
        assertInvalid(IllegalArgumentException.class, "different keys",
            () -> new PaddleControlsConfig(KeyCode.W, KeyCode.W));
    }

    private static <T extends Throwable> void assertInvalid(
        Class<T> expectedType,
        String expectedMessagePart,
        Executable operation
    ) {
        T exception = assertThrows(expectedType, operation);

        assertTrue(
            exception.getMessage().contains(expectedMessagePart),
            () -> "Expected error message to contain: " + expectedMessagePart
        );
    }
}
