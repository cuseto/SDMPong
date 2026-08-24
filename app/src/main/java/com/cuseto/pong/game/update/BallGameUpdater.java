package com.cuseto.pong.game.update;

import com.cuseto.pong.game.physics.BallMovement;
import com.cuseto.pong.game.session.GameSession;

/**
 * A {@link GameUpdater} that advances the ball's position for one frame,
 * delegating wall- and paddle-bounce physics to {@link BallMovement}.
 *
 * <p>Does nothing while the game is not {@linkplain GameSession#isGameOn() running}
 * (e.g. paused or already finished).
 */
public class BallGameUpdater implements GameUpdater {
    /**
     * Creates a new ball updater.
     */
    public BallGameUpdater() {
    }
    
    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
        if (gameSession.isGameOn()) {
            BallMovement.move(
                gameSession.ball(),
                elapsedSeconds,
                gameSession.arena().innerTopBoundary(),
                gameSession.arena().innerBottomBoundary(),
                gameSession.leftPaddle(),
                gameSession.rightPaddle(),
                gameSession.isBallSpeedIncreaseEnabled()
            );
        }
    }
}
