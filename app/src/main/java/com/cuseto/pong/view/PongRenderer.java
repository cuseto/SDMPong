package com.cuseto.pong.view;

import com.cuseto.pong.game.model.Arena;
import com.cuseto.pong.game.model.view.BallView;
import com.cuseto.pong.game.model.view.PaddleView;
import com.cuseto.pong.game.session.GameSession;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public final class PongRenderer {
    private final Canvas canvas;

    public PongRenderer(double width, double height) {
        this.canvas = new Canvas(width, height);
    }

    public void render(GameSession gameSession) {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        double screenWidth = canvas.getWidth();
        double screenHeight = canvas.getHeight();

        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, screenWidth, screenHeight);

        drawArenaBoundaries(graphics, gameSession.arena());
        drawBall(graphics, gameSession.ballInfo());
        drawPaddle(graphics, gameSession.leftPaddleInfo());
        drawPaddle(graphics, gameSession.rightPaddleInfo());
        drawMatchStatus(graphics, gameSession);
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

    private void drawPaddle(GraphicsContext graphics, PaddleView paddle) {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(
            paddle.x(), 
            paddle.y(), 
            paddle.width(), 
            paddle.height()
        );
    }

    private void drawMatchStatus(
        GraphicsContext graphics,
        GameSession gameSession
    ) {
        Arena arena = gameSession.arena();

        graphics.setFill(Color.WHITE);
        graphics.setFont(Font.font("Monospaced", FontWeight.BOLD, 28));

        graphics.setTextAlign(TextAlignment.LEFT);
        graphics.fillText(
            "P1 SCORE: " + gameSession.leftScore(),
            arena.anchorX(),
            32
        );

        graphics.setTextAlign(TextAlignment.RIGHT);
        graphics.fillText(
            "P2 SCORE: " + gameSession.rightScore(),
            arena.anchorX() + arena.width(),
            32
        );
    }

    public Canvas canvas() {
        return canvas;
    }

}
