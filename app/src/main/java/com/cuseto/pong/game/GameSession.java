package com.cuseto.pong.game;

import com.cuseto.pong.config.model.AppConfig;
import com.cuseto.pong.model.Arena;
import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.Paddle;

public final class GameSession {
    public final Arena arena;
    public final Paddle leftPaddle;
    public final Paddle rightPaddle;
    public final Ball ball;

    public GameSession(AppConfig appConfig) {
        this.arena = getArena(appConfig);
        this.leftPaddle = getLeftPaddle(appConfig, arena);
        this.rightPaddle = getRightPaddle(appConfig, arena);
        this.ball = getBall(appConfig, arena);
    }

    private Arena getArena(AppConfig appConfig) {
        int width = appConfig.viewport().screenWidth() - 2 * appConfig.gamePage().arena().spacingOther();
        int height = appConfig.viewport().screenHeight() -
            appConfig.gamePage().arena().spacingTop() -
            appConfig.gamePage().arena().spacingOther();
        int anchorX = appConfig.gamePage().arena().spacingOther();
        int anchorY = appConfig.gamePage().arena().spacingTop();

        return new Arena(
            width, 
            height, 
            anchorX, 
            anchorY, 
            appConfig.gamePage().arena().boundaryThickness()
        );
    }

    private Paddle getLeftPaddle(AppConfig appConfig, Arena arena) {
        double x = arena.innerLeftBoundary() + appConfig.gamePage().paddle().inset();
        double y = arena.innerTopBoundary() + ((arena.innerHeight() - appConfig.gamePage().paddle().height()) / 2);
        return new Paddle(
            x,
            y, 
            appConfig.gamePage().paddle().width(),
            appConfig.gamePage().paddle().height(),
            appConfig.gamePage().paddle().speed()
        );
    }

    private Paddle getRightPaddle(AppConfig appConfig, Arena arena) {
        double x = arena.innerRightBoundary() -
            appConfig.gamePage().paddle().inset() -
            appConfig.gamePage().paddle().width();
        double y = arena.innerTopBoundary() + ((arena.innerHeight() - appConfig.gamePage().paddle().height()) / 2);
        return new Paddle(
            x,
            y, 
            appConfig.gamePage().paddle().width(),
            appConfig.gamePage().paddle().height(),
            appConfig.gamePage().paddle().speed()
        );
    }

    private Ball getBall(AppConfig appConfig, Arena arena) {
        double x = arena.innerLeftBoundary() + (arena.innerWidth() / 2);
        double y = arena.innerTopBoundary() + (arena.innerHeight() / 2);
        return new Ball(
            x,
            y,
            appConfig.gamePage().ball().radius(),
            appConfig.gamePage().ball().initialVelocityX(),
            appConfig.gamePage().ball().initialVelocityY()
        );
    }
}
