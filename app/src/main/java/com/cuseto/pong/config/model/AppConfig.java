package com.cuseto.pong.config.model;

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
