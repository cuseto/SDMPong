package com.cuseto.pong.config.schema;

import com.cuseto.pong.config.schema.controls.ControlsConfig;
import com.cuseto.pong.config.schema.game.GamePageConfig;
import com.cuseto.pong.config.validation.ConfigValidation;

/**
 * The root of the application's configuration, combining viewport, match,
 * and control settings.
 *
 * @param viewport the application window's size; must not be {@code null}
 * @param gamePage the match's arena, ball, paddle, and winning-score settings; must not be {@code null}
 * @param controls the key bindings for both paddles; must not be {@code null}
 */
public record AppConfig(
    ViewportConfig viewport,
    GamePageConfig gamePage,
    ControlsConfig controls
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code viewport}, {@code gamePage}, or {@code controls} is {@code null}
     */
    public AppConfig {
        ConfigValidation.requireNonNull("viewport", viewport);
        ConfigValidation.requireNonNull("gamePage", gamePage);
        ConfigValidation.requireNonNull("controls", controls);
    }
}
