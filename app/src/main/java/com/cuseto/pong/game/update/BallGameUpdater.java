package com.cuseto.pong.game.update;

import com.cuseto.pong.game.physics.BallMovement;
import com.cuseto.pong.game.session.GameSession;

public class BallGameUpdater implements GameUpdater {
    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
        if (gameSession.isGameOn()) {
            BallMovement.move(
                gameSession.ball(),
                elapsedSeconds,
                gameSession.arena().innerTopBoundary(),
                gameSession.arena().innerBottomBoundary(),
                gameSession.leftPaddle(),
                gameSession.rightPaddle()
            );
        }
    }
}
