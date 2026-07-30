package com.cuseto.pong.config;

public record GamePageConfig(
    ArenaConfig arenaConfig,
    BallConfig ballConfig,
    PaddleConfig paddleConfig
) {}