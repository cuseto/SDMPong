package com.cuseto.pong.game.input;

import java.util.Objects;

import com.cuseto.pong.config.schema.controls.ControlsConfig;
import com.cuseto.pong.game.model.PaddleDirection;

import javafx.scene.input.KeyCode;

public final class PaddleKeyMapping {
    private final ControlsConfig controls;

    public PaddleKeyMapping(ControlsConfig controls) {
        this.controls = Objects.requireNonNull(controls, "controls cannot be null");
    }

    public PaddleDirection leftDirectionFor(KeyCode keyCode) {
        if (keyCode == controls.leftPaddle().up()) {
            return PaddleDirection.UP;
        }

        if (keyCode == controls.leftPaddle().down()) {
            return PaddleDirection.DOWN;
        }

        return PaddleDirection.NONE;
    }

    public PaddleDirection rightDirectionFor(KeyCode keyCode) {
        if (keyCode == controls.rightPaddle().up()) {
            return PaddleDirection.UP;
        }

        if (keyCode == controls.rightPaddle().down()) {
            return PaddleDirection.DOWN;
        }

        return PaddleDirection.NONE;
    }
}