package com.cuseto.pong.model;

import java.util.Objects;

public record GameState(	// For things that can change during gameplay
    int arenaWidth,
    int arenaHeight,
    int arenaBoundaryThickness,
	Circle ball,
    Rectangle leftPaddle,
    Rectangle rightPaddle
) {

    public static GameState initial(GameConfig config) {
        Objects.requireNonNull(config, "config cannot be null");

        double paddleY = (config.arenaHeight() - config.paddleHeight()) / 2.0;

        Circle ball = new Circle(
            config.arenaWidth() / 2.0, 
            config.arenaHeight() / 2.0, 
            config.ballRadius()
        );

        Rectangle leftPaddle = new Rectangle(
                config.paddleInset(),
                paddleY,
                config.paddleWidth(),
                config.paddleHeight()
        );
        Rectangle rightPaddle = new Rectangle(
                config.arenaWidth() - config.paddleInset() - config.paddleWidth(),
                paddleY,
                config.paddleWidth(),
                config.paddleHeight()
        );

        return new GameState(		
            config.arenaWidth(),
            config.arenaHeight(),
            config.arenaBoundaryThickness(),
            ball,
            leftPaddle,
            rightPaddle
        );
    }
}
