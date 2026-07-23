package com.cuseto.pong.game;

import com.cuseto.pong.model.GameState;

/**
 * Functional interface that requires to implement the update logic.
 */
@FunctionalInterface
public interface GameUpdater {
    GameState update(GameState state, double elapsedSeconds);

    default GameUpdater andThen(GameUpdater next) {
        return (state, elapsedSeconds) ->
            next.update(this.update(state, elapsedSeconds), elapsedSeconds);
    }
}
