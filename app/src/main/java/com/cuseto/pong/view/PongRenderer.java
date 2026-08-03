package com.cuseto.pong.view;

import com.cuseto.pong.game.GameSession;
import com.cuseto.pong.model.Arena;
import com.cuseto.pong.model.Paddle;
import com.cuseto.pong.model.view.BallView;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class PongRenderer {

    public void render(Canvas canvas, GameSession gameSession) {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        double screenWidth = canvas.getWidth();
        double screenHeight = canvas.getHeight();

        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, screenWidth, screenHeight);

        drawArenaBoundaries(graphics, gameSession.arena);
        drawBall(graphics, gameSession.ballInfo());
        drawPaddle(graphics, gameSession.leftPaddle);
        drawPaddle(graphics, gameSession.rightPaddle);
    }

    private void drawArenaBoundaries(GraphicsContext graphics, Arena arena) {
        graphics.setStroke(Color.WHITE);
        graphics.setLineWidth(arena.boundaryThickness());

        graphics.strokeRect(
            arena.anchorX(),
            arena.anchorY(),
            arena.width(),
            arena.height()
        );    
    }

    private void drawBall(GraphicsContext graphics, BallView ball) {
        graphics.setFill(Color.WHITE);
        double diameter = 2.0 * ball.radius();
        graphics.fillOval(
            ball.x() - ball.radius(),
            ball.y() - ball.radius(),
            diameter,
            diameter
        );
    }

    private void drawPaddle(GraphicsContext graphics, Paddle paddle) {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(
            paddle.x(), 
            paddle.y(), 
            paddle.width(), 
            paddle.height()
        );
    }

}
