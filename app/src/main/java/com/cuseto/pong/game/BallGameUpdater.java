package com.cuseto.pong.game;

public class BallGameUpdater implements GameUpdater {
    private final GameSession gameSession;

    public BallGameUpdater(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
        BallMovement.move(
            gameSession.ball(),
            elapsedSeconds,
            gameSession.arena.innerTopBoundary(),
            gameSession.arena.innerBottomBoundary(),
            gameSession.leftPaddle(),
            gameSession.rightPaddle()
        );

    }
}
