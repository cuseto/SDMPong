package com.cuseto.pong.game.update;

import com.cuseto.pong.game.session.GameSession;

/**
 * Updates a game state for one frame.
 */
@FunctionalInterface
public interface GameUpdater {
    void update(GameSession state, double elapsedSeconds);

    default GameUpdater andThen(GameUpdater next) {
        return (state, elapsedSeconds) -> {
            update(state, elapsedSeconds);
            next.update(state, elapsedSeconds);
        };
    }
}
