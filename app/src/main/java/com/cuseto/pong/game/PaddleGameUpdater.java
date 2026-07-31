package com.cuseto.pong.game;

public class PaddleGameUpdater implements GameUpdater {

    private final PaddleInputState inputState;
    private final GameSession gameSession;

    public PaddleGameUpdater(PaddleInputState inputState, GameSession gameSession) {
        this.inputState = inputState;
        this.gameSession = gameSession;
    }

    @Override
    public void update(GameSession state, double elapsedSeconds) {
        double minY = gameSession.arena.innerTopBoundary();
        double maxY = gameSession.arena.innerBottomBoundary();

        PaddleMovement.move(
            state.leftPaddle,
            inputState.leftDirection(),
            elapsedSeconds,
            state.leftPaddle.speed(),
            minY,
            maxY - gameSession.leftPaddle.height()
        );

        PaddleMovement.move(
            state.rightPaddle,
            inputState.rightDirection(),
            elapsedSeconds,
            state.rightPaddle.speed(),
            minY,
            maxY - gameSession.rightPaddle.height()
        );

    }
}
