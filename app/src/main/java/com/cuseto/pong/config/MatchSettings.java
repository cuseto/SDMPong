package com.cuseto.pong.config;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.config.schema.game.BallConfig;
import com.cuseto.pong.config.schema.game.GamePageConfig;
import com.cuseto.pong.config.schema.game.PaddleConfig;

public final class MatchSettings {
    private final int winningScore;
    private final double ballSpeed;
    private final double paddleSpeed;
    private final boolean ballSpeedIncreaseEnabled;

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

    public int winningScore() {
        return winningScore;
    }

    public double ballSpeed() {
        return ballSpeed;
    }

    public double paddleSpeed() {
        return paddleSpeed;
    }

    public boolean ballSpeedIncreaseEnabled() {
        return ballSpeedIncreaseEnabled;
    }

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
