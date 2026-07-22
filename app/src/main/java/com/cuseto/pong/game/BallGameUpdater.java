package com.cuseto.pong.game;

import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;

public class BallGameUpdater implements GameUpdater {
    private final GameConfig config;

    public BallGameUpdater(GameConfig config) {
        this.config = config;
    }

    @Override
    public GameState update(GameState state, double elapsedSeconds) {
        return new GameState(
            BallMovement.move(
                state.ball(),
                elapsedSeconds,
                config.arenaTop() + config.arenaBoundaryThickness(),
                config.arenaBottom() - config.arenaBoundaryThickness()
            ),
            state.leftPaddle(),
            state.rightPaddle()
        );
    }
}
