package com.cuseto.pong.model;

import java.util.Objects;

/**
 * Owns the mutable objects that make up one match.
 */
public final class GameState {
    private final Ball ball;
    private final Paddle leftPaddle;
    private final Paddle rightPaddle;

    public GameState(Ball ball, Paddle leftPaddle, Paddle rightPaddle) {
        this.ball = Objects.requireNonNull(ball, "ball cannot be null");
        this.leftPaddle = Objects.requireNonNull(leftPaddle, "leftPaddle cannot be null");
        this.rightPaddle = Objects.requireNonNull(rightPaddle, "rightPaddle cannot be null");
    }

    public Ball ball() {
        return ball;
    }

    public Paddle leftPaddle() {
        return leftPaddle;
    }

    public Paddle rightPaddle() {
        return rightPaddle;
    }

    public static GameState initial(GameConfig config) {
        Objects.requireNonNull(config, "config cannot be null");

        double paddleY = config.arenaSpacingTop() + (config.arenaHeight() - config.paddleHeight()) / 2.0;

        Ball ball = new Ball(
            config.arenaSpacingOther() + config.arenaWidth() / 2.0,  // == screenWidth / 2
            config.arenaSpacingTop() + config.arenaHeight() / 2.0, 
            config.ballRadius(),
            config.ballVelocityX(),
            config.ballVelocityY()
        );

        Paddle leftPaddle = new Paddle(
            config.arenaSpacingOther() + config.arenaBoundaryThickness() + config.paddleInset(),
            paddleY,
            config.paddleWidth(),
            config.paddleHeight()
        );
        Paddle rightPaddle = new Paddle(
            config.screenWidth() - config.arenaSpacingOther() - config.arenaBoundaryThickness() - config.paddleInset() - config.paddleWidth(),
            paddleY,
            config.paddleWidth(),
            config.paddleHeight()
        );

        return new GameState(
            ball,
            leftPaddle,
            rightPaddle
        );
    }
}
