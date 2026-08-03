package com.cuseto.pong.game;

public class BallGameUpdater implements GameUpdater {
    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
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
