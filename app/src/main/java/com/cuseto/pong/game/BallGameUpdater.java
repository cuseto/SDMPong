package com.cuseto.pong.game;

import com.cuseto.pong.model.GameState;

public class BallGameUpdater implements GameUpdater {

    @Override
    public GameState update(GameState state, double elapsedSeconds) {
        return new GameState(
            BallMovement.move(state.ball(), elapsedSeconds),
            state.leftPaddle(),
            state.rightPaddle()
        );
    }
}
