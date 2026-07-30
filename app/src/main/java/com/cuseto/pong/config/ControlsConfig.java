package com.cuseto.pong.config;

public record ControlsConfig(
    PaddleControlsConfig leftPaddle,
    PaddleControlsConfig rightPaddle
) {}
