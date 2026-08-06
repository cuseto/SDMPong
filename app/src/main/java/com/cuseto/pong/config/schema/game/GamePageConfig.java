package com.cuseto.pong.config.schema.game;

import com.cuseto.pong.config.validation.ConfigValidation;

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
