package com.cuseto.pong.config;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.config.schema.game.BallConfig;
import com.cuseto.pong.config.schema.game.GamePageConfig;
import com.cuseto.pong.config.schema.game.PaddleConfig;

/**
 * The subset of match settings a player can edit from the options menu:
 * winning score, ball and paddle speed, and whether ball speed increases
 * on paddle bounces.
 *
 * <p>This is a plain validated value holder, distinct from the
 * configuration schema records; {@link #applyTo(AppConfig)} merges these
 * values into an existing {@link AppConfig}, leaving arena, paddle
 * size/inset, and ball radius untouched.
 */
public final class MatchSettings {
    private final int winningScore;
    private final double ballSpeed;
    private final double paddleSpeed;
    private final boolean ballSpeedIncreaseEnabled;

    /**
     * Creates a validated set of match settings.
     *
     * @param winningScore the score a player must reach to win the match; must be positive
     * @param ballSpeed the ball's initial horizontal and vertical speed, in units per second; must be positive and finite
     * @param paddleSpeed the paddles' movement speed, in units per second; must be positive and finite
     * @param ballSpeedIncreaseEnabled whether the ball's horizontal speed increases on each paddle bounce
     * @throws IllegalArgumentException if {@code winningScore} is not positive, or if {@code ballSpeed} or {@code paddleSpeed} is not positive and finite
     */
    public MatchSettings(
        int winningScore,
        double ballSpeed,
        double paddleSpeed,
        boolean ballSpeedIncreaseEnabled
    ) {
        if (winningScore <= 0) {
            throw new IllegalArgumentException("Winning score must be greater than zero");
        }

        if (ballSpeed <= 0 || !Double.isFinite(ballSpeed)) {
            throw new IllegalArgumentException(
                "Ball speed must be greater than zero and finite"
            );
        }

        if (paddleSpeed <= 0 || !Double.isFinite(paddleSpeed)) {
            throw new IllegalArgumentException(
                "Paddle speed must be greater than zero and finite"
            );
        }

        this.winningScore = winningScore;
        this.ballSpeed = ballSpeed;
        this.paddleSpeed = paddleSpeed;
        this.ballSpeedIncreaseEnabled = ballSpeedIncreaseEnabled;
    }

    /**
     * Returns the score a player must reach to win the match.
     *
     * @return the winning score
     */
    public int winningScore() {
        return winningScore;
    }

    /**
     * Returns the ball's initial speed.
     *
     * @return the ball's initial horizontal and vertical speed, in units per second
     */
    public double ballSpeed() {
        return ballSpeed;
    }

    /**
     * Returns the paddles' movement speed.
     *
     * @return the paddles' movement speed, in units per second
     */
    public double paddleSpeed() {
        return paddleSpeed;
    }

    /**
     * Returns whether the ball's horizontal speed increases on each paddle bounce.
     *
     * @return {@code true} if ball speed increases on paddle bounces, {@code false} otherwise
     */
    public boolean ballSpeedIncreaseEnabled() {
        return ballSpeedIncreaseEnabled;
    }

    /**
     * Returns a copy of {@code config} with this object's winning score,
     * ball speed, paddle speed, and speed-increase flag applied. Arena
     * spacing, paddle size/inset, ball radius, viewport, and controls are
     * carried over unchanged from {@code config}.
     *
     * @param config the configuration to merge these settings into; must not be {@code null}
     * @return a new {@code AppConfig} with these match settings applied
     */
    public AppConfig applyTo(AppConfig config) {
        GamePageConfig gamePage = config.gamePage();

        BallConfig ball = gamePage.ball();
        PaddleConfig paddle = gamePage.paddle();

        BallConfig updatedBall = new BallConfig(
            ball.radius(),
            ballSpeed,
            ballSpeed,
            ballSpeedIncreaseEnabled
        );

        PaddleConfig updatedPaddle = new PaddleConfig(
            paddle.width(),
            paddle.height(),
            paddle.inset(),
            paddleSpeed
        );

        GamePageConfig updatedGamePage = new GamePageConfig(
            gamePage.arena(),
            updatedBall,
            updatedPaddle,
            winningScore
        );

        return new AppConfig(
            config.viewport(),
            updatedGamePage,
            config.controls()
        );
    }
}
