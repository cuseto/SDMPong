package com.cuseto.pong.game.update;

import com.cuseto.pong.game.input.PaddleInputState;
import com.cuseto.pong.game.physics.PaddleMovement;
import com.cuseto.pong.game.session.GameSession;

public class PaddleGameUpdater implements GameUpdater {
    private final PaddleInputState inputState;

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
