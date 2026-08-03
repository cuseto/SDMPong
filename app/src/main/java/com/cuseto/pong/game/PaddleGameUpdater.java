package com.cuseto.pong.game;

public class PaddleGameUpdater implements GameUpdater {
    private final PaddleInputState inputState;

    public PaddleGameUpdater(PaddleInputState inputState) {
        this.inputState = inputState;
    }

    @Override
    public void update(GameSession gameSession, double elapsedSeconds) {
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
