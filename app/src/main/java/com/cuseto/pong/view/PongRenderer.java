package com.cuseto.pong.view;

import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.session.Player;
import com.cuseto.pong.model.Arena;
import com.cuseto.pong.model.view.BallView;
import com.cuseto.pong.model.view.PaddleView;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public final class PongRenderer {

    public void render(Canvas canvas, GameSession gameSession) {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        double screenWidth = canvas.getWidth();
        double screenHeight = canvas.getHeight();

        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, screenWidth, screenHeight);

        drawArenaBoundaries(graphics, gameSession.arena());
        drawBall(graphics, gameSession.ballInfo());
        drawPaddle(graphics, gameSession.leftPaddleInfo());
        drawPaddle(graphics, gameSession.rightPaddleInfo());
        drawMatchStatus(graphics, gameSession, screenWidth, screenHeight);
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
        GameSession gameSession,
        double screenWidth,
        double screenHeight
    ) {
        graphics.setFill(Color.WHITE);
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setFont(Font.font(20));
        graphics.fillText(
            "LEFT: " + gameSession.leftScore() + "    RIGHT: " + gameSession.rightScore(),
            screenWidth / 2.0,
            32
        );

        if (gameSession.isMatchOver()) {
            Player winner = gameSession.winner();
            graphics.setFill(Color.GRAY);
            graphics.fillRect(0, screenHeight / 2.0 - 48, screenWidth, 96);
            graphics.setFill(Color.WHITE);
            graphics.setFont(Font.font(30));
            graphics.fillText(
                winner == Player.LEFT ? "LEFT PLAYER WINS!" : "RIGHT PLAYER WINS!",
                screenWidth / 2.0,
                screenHeight / 2.0 - 8
            );
            graphics.setFont(Font.font(18));
            graphics.fillText(
                "ENTER: NEW MATCH    ESC: QUIT",
                screenWidth / 2.0,
                screenHeight / 2.0 + 25
            );
        }
    }

}
