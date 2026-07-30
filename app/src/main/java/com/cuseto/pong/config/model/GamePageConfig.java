package com.cuseto.pong.config.model;

public record GamePageConfig(
    ArenaConfig arena,
    BallConfig ball,
    PaddleConfig paddle
) {
    public GamePageConfig {
        ConfigValidation.requireNonNull("arena", arena);
        ConfigValidation.requireNonNull("ball", ball);
        ConfigValidation.requireNonNull("paddle", paddle);
    }
}
