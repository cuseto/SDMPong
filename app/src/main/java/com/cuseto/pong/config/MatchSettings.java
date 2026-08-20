package com.cuseto.pong.config;

public final class MatchSettings {
    private final int winningScore;
    private final double ballSpeed;
    private final double paddleSpeed;

    public MatchSettings(int winningScore, double ballSpeed, double paddleSpeed) {
        if (winningScore <= 0) {
            throw new IllegalArgumentException("winningScore must be positive");
        }

        if (ballSpeed <= 0 || !Double.isFinite(ballSpeed)) {
            throw new IllegalArgumentException("ballSpeed must be positive and finite");
        }

        if (paddleSpeed <= 0 || !Double.isFinite(paddleSpeed)) {
            throw new IllegalArgumentException("paddleSpeed must be positive and finite");
        }

        this.winningScore = winningScore;
        this.ballSpeed = ballSpeed;
        this.paddleSpeed = paddleSpeed;
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
}
