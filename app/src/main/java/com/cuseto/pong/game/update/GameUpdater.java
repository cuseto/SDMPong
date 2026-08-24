package com.cuseto.pong.game.update;

import com.cuseto.pong.game.session.GameSession;

/**
 * Updates a game state for one frame.
 */
@FunctionalInterface
public interface GameUpdater {
    /**
     * Applies one frame's worth of update to the given game session.
     *
     * @param state the game session to update, in place
     * @param elapsedSeconds the amount of simulated time to advance, in seconds
     */
    void update(GameSession state, double elapsedSeconds);

    /**
     * Composes this updater with another, so {@code next} runs immediately
     * after this one on every frame.
     *
     * @param next the updater to run after this one
     * @return a combined updater that applies this updater, then {@code next}
     */
    default GameUpdater andThen(GameUpdater next) {
        return (state, elapsedSeconds) -> {
            update(state, elapsedSeconds);
            next.update(state, elapsedSeconds);
        };
    }
}
