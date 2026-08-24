package com.cuseto.pong.game.input;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.schema.controls.ControlsConfig;
import com.cuseto.pong.config.schema.controls.PaddleControlsConfig;
import com.cuseto.pong.game.model.PaddleDirection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaddleKeyMappingTest {
    @Test
    void configuredKeysMapToConfiguredLeftPaddleDirections() {
        ControlsConfig controls = new ControlsConfig(
            new PaddleControlsConfig(KeyCode.A, KeyCode.Z),
            new PaddleControlsConfig(KeyCode.UP, KeyCode.DOWN)
        );

        PaddleKeyMapping mapping = new PaddleKeyMapping(controls);

        assertEquals(
            PaddleDirection.UP,
            mapping.leftDirectionFor(KeyCode.A)
        );
        assertEquals(
            PaddleDirection.DOWN,
            mapping.leftDirectionFor(KeyCode.Z)
        );
    }

    @Test
    void configuredKeysMapToConfiguredRightPaddleDirections() {
        ControlsConfig controls = new ControlsConfig(
            new PaddleControlsConfig(KeyCode.W, KeyCode.S),
            new PaddleControlsConfig(KeyCode.I, KeyCode.K)
        );

        PaddleKeyMapping mapping = new PaddleKeyMapping(controls);

        assertEquals(
            PaddleDirection.UP,
            mapping.rightDirectionFor(KeyCode.I)
        );
        assertEquals(
            PaddleDirection.DOWN,
            mapping.rightDirectionFor(KeyCode.K)
        );
    }

    @Test
    void unrelatedKeyMapsToNone() {
        ControlsConfig controls = new ControlsConfig(
            new PaddleControlsConfig(KeyCode.A, KeyCode.Z),
            new PaddleControlsConfig(KeyCode.I, KeyCode.K)
        );

        PaddleKeyMapping mapping = new PaddleKeyMapping(controls);

        assertEquals(
            PaddleDirection.NONE,
            mapping.leftDirectionFor(KeyCode.X)
        );
        assertEquals(
            PaddleDirection.NONE,
            mapping.rightDirectionFor(KeyCode.X)
        );
    }
}
