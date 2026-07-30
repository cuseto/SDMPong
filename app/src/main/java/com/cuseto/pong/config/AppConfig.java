package com.cuseto.pong.config;

public record AppConfig(
    ViewportConfig viewport,
    GamePageConfig gamePage,
    ControlsConfig controls
) {}
