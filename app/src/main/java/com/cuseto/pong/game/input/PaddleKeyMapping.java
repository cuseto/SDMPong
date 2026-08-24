package com.cuseto.pong.game.input;

import java.util.Objects;

import com.cuseto.pong.config.schema.controls.ControlsConfig;
import com.cuseto.pong.game.model.PaddleDirection;

import javafx.scene.input.KeyCode;

/**
 * Translates raw JavaFX key codes into paddle movement directions, based on
 * the key bindings configured for each paddle.
 *
 * <p>This class only maps a key to a direction; it does not track whether a
 * key is currently held down or update any state itself. Callers (e.g.
 * {@code GameplayController}'s key event handlers) use it on both key-press
 * and key-release events to decide what to write into {@link PaddleInputState}.
 */
public final class PaddleKeyMapping {
    private final ControlsConfig controls;

    /**
     * Creates a key mapping backed by the given controls configuration.
     *
     * @param controls the left/right paddle key bindings to map against; must not be {@code null}
     * @throws NullPointerException if {@code controls} is {@code null}
     */
    public PaddleKeyMapping(ControlsConfig controls) {
        this.controls = Objects.requireNonNull(controls, "controls cannot be null");
    }

    /**
     * Determines the left paddle's movement direction for the given key.
     *
     * @param keyCode the key to map
     * @return {@link PaddleDirection#UP} or {@link PaddleDirection#DOWN} if
     *         {@code keyCode} matches the left paddle's configured up/down
     *         key, otherwise {@link PaddleDirection#NONE}
     */
    public PaddleDirection leftDirectionFor(KeyCode keyCode) {
        if (keyCode == controls.leftPaddle().up()) {
            return PaddleDirection.UP;
        }

        if (keyCode == controls.leftPaddle().down()) {
            return PaddleDirection.DOWN;
        }

        return PaddleDirection.NONE;
    }

    /**
     * Determines the right paddle's movement direction for the given key.
     *
     * @param keyCode the key to map
     * @return {@link PaddleDirection#UP} or {@link PaddleDirection#DOWN} if
     *         {@code keyCode} matches the right paddle's configured up/down
     *         key, otherwise {@link PaddleDirection#NONE}
     */
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