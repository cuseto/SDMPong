package com.cuseto.pong.config.schema.game;

import com.cuseto.pong.config.validation.ConfigValidation;

/**
 * The configuration for a single match: arena, ball, and paddle settings,
 * plus the score needed to win.
 *
 * @param arena the arena's spacing and wall thickness; must not be {@code null}
 * @param ball the ball's size, initial velocity, and speed-up behavior; must not be {@code null}
 * @param paddle the paddles' size, inset, and speed; must not be {@code null}
 * @param winningScore the score a player must reach to win the match; must be positive
 */
public record GamePageConfig(
    ArenaConfig arena,
    BallConfig ball,
    PaddleConfig paddle,
    int winningScore
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code arena}, {@code ball}, or {@code paddle} is {@code null}, or if {@code winningScore} is not positive
     */
    public GamePageConfig {
        ConfigValidation.requireNonNull("arena", arena);
        ConfigValidation.requireNonNull("ball", ball);
        ConfigValidation.requireNonNull("paddle", paddle);
        ConfigValidation.requirePositive("winningScore", winningScore);
    }
}
