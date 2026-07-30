package com.cuseto.pong.game;

import com.cuseto.pong.model.GameState;

/**
 * Updates a game state for one frame.
 */
@FunctionalInterface
public interface GameUpdater {
    void update(GameState state, double elapsedSeconds);

    default GameUpdater andThen(GameUpdater next) {
        return (state, elapsedSeconds) -> {
            update(state, elapsedSeconds);
            next.update(state, elapsedSeconds);
        };
    }
}
