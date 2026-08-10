package com.cuseto.pong.config.schema;

import com.cuseto.pong.config.schema.controls.ControlsConfig;
import com.cuseto.pong.config.schema.game.GamePageConfig;
import com.cuseto.pong.config.validation.ConfigValidation;

public record AppConfig(
    ViewportConfig viewport,
    GamePageConfig gamePage,
    ControlsConfig controls
) {
    public AppConfig {
        ConfigValidation.requireNonNull("viewport", viewport);
        ConfigValidation.requireNonNull("gamePage", gamePage);
        ConfigValidation.requireNonNull("controls", controls);
    }
}
