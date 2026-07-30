package com.cuseto.pong.config;

public record AppConfig(
    ViewportConfig viewportConfig,
    GamePageConfig gamePageConfig,
    ControlsConfig controlsConfig
) {}