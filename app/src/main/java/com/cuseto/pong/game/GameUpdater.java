package com.cuseto.pong.game;

import com.cuseto.pong.model.GameState;

/**
 * Functional interface that requires to implement the update logic.
 * The current logic is static: given the current GameState and elapsed time,
 * return the same current GameState (in App.java, line 36):
 * 
 *     (currentState, elapsedSeconds) -> currentState,
 */
@FunctionalInterface
public interface GameUpdater {
    GameState update(GameState state, double elapsedSeconds);

    default GameUpdater andThen(GameUpdater next) {
        return (state, elapsedSeconds) ->
            next.update(this.update(state, elapsedSeconds), elapsedSeconds);
    }
}
