package com.cuseto.pong.model;

import java.util.Objects;

public record GameState(	// For things that can change during gameplay
    int arenaWidth,
    int arenaHeight,
	Circle ball
) {

    public static GameState initial(GameConfig config) {
        Objects.requireNonNull(config, "config cannot be null");

        return new GameState(		
            config.arenaWidth(),
            config.arenaHeight(),
			new Circle(400, 300, 8)
        );
    }
}
