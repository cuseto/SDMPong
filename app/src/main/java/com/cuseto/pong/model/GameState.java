package com.cuseto.pong.model;

import java.util.Objects;

public record GameState(    // For things that can change during gameplay
	Circle ball,
    Rectangle leftPaddle,
    Rectangle rightPaddle
) {
    public GameState {
        Objects.requireNonNull(ball, "ball cannot be null");
        Objects.requireNonNull(leftPaddle, "leftPaddle cannot be null");
        Objects.requireNonNull(rightPaddle, "rightPaddle cannot be null");
    }

    public static GameState initial(GameConfig config) {
        Objects.requireNonNull(config, "config cannot be null");

        double paddleY = config.arenaSpacingTop() + (config.arenaHeight() - config.paddleHeight()) / 2.0;

        Circle ball = new Circle(
            config.arenaSpacingOther() + config.arenaWidth() / 2.0,  // == screenWidth / 2
            config.arenaSpacingTop() + config.arenaHeight() / 2.0, 
            config.ballRadius(),
            config.ballVelocityX(),
            config.ballVelocityY()
        );

        Rectangle leftPaddle = new Rectangle(
            config.arenaSpacingOther() + config.arenaBoundaryThickness() + config.paddleInset(),
            paddleY,
            config.paddleWidth(),
            config.paddleHeight()
        );
        Rectangle rightPaddle = new Rectangle(
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
