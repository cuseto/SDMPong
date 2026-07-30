package com.cuseto.pong.config;

public record GamePageConfig(
    ArenaConfig arena,
    BallConfig ball,
    PaddleConfig paddle
) {}
