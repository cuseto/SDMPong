package com.cuseto.pong.game.update;

import com.cuseto.pong.game.input.PaddleInputState;
import com.cuseto.pong.game.physics.PaddleMovement;
import com.cuseto.pong.game.session.GameSession;

/**
 * A {@link GameUpdater} that advances both paddles for one frame, based on
 * the current player input, delegating movement physics to
 * {@link PaddleMovement}.
 *
 * <p>Each paddle is clamped so it stays fully within the arena's inner
 * vertical boundaries. Does nothing while the game is not
 * {@linkplain GameSession#isGameOn() running}.
 */
public class PaddleGameUpdater implements GameUpdater {
    private final PaddleInputState inputState;

    /**
     * Creates a paddle updater backed by the given input state.
     *
     * @param inputState the source of the current left/right paddle directions, consulted on every update
     */
    public PaddleGameUpdater(PaddleInputState inputState) {
        this.inputState = inputState;
    }

    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
        if (gameSession.isGameOn()) {
            double minY = gameSession.arena().innerTopBoundary();
            double maxY = gameSession.arena().innerBottomBoundary();

            PaddleMovement.move(
                gameSession.leftPaddle(),
                inputState.leftDirection(),
                elapsedSeconds,
                minY,
                maxY - gameSession.leftPaddle().height()
            );

            PaddleMovement.move(
                gameSession.rightPaddle(),
                inputState.rightDirection(),
                elapsedSeconds,
                minY,
                maxY - gameSession.rightPaddle().height()
            );
        }
    }
}
