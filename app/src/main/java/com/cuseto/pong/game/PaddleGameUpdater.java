package com.cuseto.pong.game;

import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;
import com.cuseto.pong.model.Rectangle;

public class PaddleGameUpdater implements GameUpdater {

    private final PaddleInputState inputState;
    private final GameConfig config;

    public PaddleGameUpdater(PaddleInputState inputState, GameConfig config) {
        this.inputState = inputState;
        this.config = config;
    }

    @Override
    public GameState update(GameState state, double elapsedSeconds) {
        double minY = config.arenaTop();
        double maxY = config.arenaBottom() - config.paddleHeight();

        Rectangle leftPaddle = PaddleMovement.move(
            state.leftPaddle(),
            inputState.leftDirection(),
            elapsedSeconds,
            config.paddleSpeed(),
            minY,
            maxY
        );

        Rectangle rightPaddle = PaddleMovement.move(
            state.rightPaddle(),
            inputState.rightDirection(),
            elapsedSeconds,
            config.paddleSpeed(),
            minY,
            maxY
        );

        return new GameState(state.ball(), leftPaddle, rightPaddle);
    }
}
