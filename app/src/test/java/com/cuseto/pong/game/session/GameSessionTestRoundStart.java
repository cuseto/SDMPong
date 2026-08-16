package com.cuseto.pong.game.session;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.input.PaddleInputState;
import com.cuseto.pong.game.update.BallGameUpdater;
import com.cuseto.pong.game.update.GameUpdater;
import com.cuseto.pong.game.update.PaddleGameUpdater;


class GameSessionTestRoundStart {

    @Test
    void ballMovesFromInitialPositionOncePlayStarts() {
        AppConfig appConfig = ConfigLoader.load("/test-config.yaml");
        GameSession gameSession = new GameSession(appConfig);
        double initialBallX = gameSession.ball().x();

        GameUpdater updater = new PaddleGameUpdater(new PaddleInputState())
            .andThen(new BallGameUpdater());

        updater.update(gameSession, 0.1);

        assertNotEquals(initialBallX, gameSession.ball().x());
        assertNotEquals(0.0, gameSession.ball().velocityX());
    }
}
