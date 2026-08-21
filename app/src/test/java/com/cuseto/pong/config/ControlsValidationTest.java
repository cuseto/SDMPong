package com.cuseto.pong.config;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.schema.controls.ControlsConfig;
import com.cuseto.pong.config.schema.controls.PaddleControlsConfig;

import javafx.scene.input.KeyCode;

class ControlsValidationTest {
    @Test
    void paddleUpAndDownBindingsMustDiffer() {
        assertThrows(IllegalArgumentException.class, () -> new PaddleControlsConfig(KeyCode.A, KeyCode.A));
    }

    @Test
    void bindingsCannotBeDuplicatedAcrossPaddles() {
        assertThrows(
            IllegalArgumentException.class, 
            () -> new ControlsConfig(
                new PaddleControlsConfig(KeyCode.A, KeyCode.Z), 
                new PaddleControlsConfig(KeyCode.A, KeyCode.DOWN)
            )
        );
    }

    @Test
    void escapeCannotBeAssignedAsPaddleControl() {
        assertThrows(IllegalArgumentException.class, () -> new PaddleControlsConfig(KeyCode.ESCAPE, KeyCode.Z));
    }
}
