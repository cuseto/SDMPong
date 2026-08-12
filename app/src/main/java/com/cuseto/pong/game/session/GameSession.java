package com.cuseto.pong.game.session;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.model.Arena;
import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.Paddle;
import com.cuseto.pong.model.view.BallView;
import com.cuseto.pong.model.view.PaddleView;

public final class GameSession {
    private final Arena arena;
    private final Paddle leftPaddle;
    private final Paddle rightPaddle;
    private final Ball ball;
    private final double initialBallVelocityX;
    private final double initialBallVelocityY;
    private int leftScore;
    private int rightScore;

    public GameSession(AppConfig appConfig) {
        this.arena = getArena(appConfig);
        this.leftPaddle = getLeftPaddle(appConfig, arena);
        this.rightPaddle = getRightPaddle(appConfig, arena);

        this.initialBallVelocityX = appConfig.gamePage().ball().initialVelocityX();
        this.initialBallVelocityY = appConfig.gamePage().ball().initialVelocityY();

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

    private double initialPaddleY(Paddle paddle) {
        return arena.innerTopBoundary() + ((arena.innerHeight() - paddle.height()) / 2.0);
    }

    private Ball getBall(AppConfig appConfig, Arena arena) {
        double x = initialBallX(arena);
        double y = initialBallY(arena);
        return new Ball(
            x,
            y,
            appConfig.gamePage().ball().radius(),
            initialBallVelocityX,
            initialBallVelocityY
        );
    }

    private double initialBallX(Arena arena) {
        return arena.innerLeftBoundary() + arena.innerWidth() / 2.0;
    }

    private double initialBallY(Arena arena) {
        return arena.innerTopBoundary() + arena.innerHeight() / 2.0;
    }

    public void resetRound() {
        ball.setX(initialBallX(arena));
        ball.setY(initialBallY(arena));
        ball.setVelocity(initialBallVelocityX, initialBallVelocityY);

        leftPaddle.moveToY(initialPaddleY(leftPaddle));
        rightPaddle.moveToY(initialPaddleY(rightPaddle));
    }

    // getter
    public BallView ballInfo() {
        return this.ball;
    }

    public PaddleView leftPaddleInfo() {
        return this.leftPaddle;
    }

    public PaddleView rightPaddleInfo() {
        return this.rightPaddle;
    }

    public Arena arena() {
        return this.arena;
    }

    public Ball ball() {
        return this.ball;
    }

    public Paddle leftPaddle() {
        return this.leftPaddle;
    }

    public Paddle rightPaddle() {
        return this.rightPaddle;
    }

    public int leftScore() {
        return leftScore;
    }

    public int rightScore() {
        return rightScore;
    }

    public void incrementLeftScore() {
        leftScore++;
    }

    public void incrementRightScore() {
        rightScore++;
    }
}
