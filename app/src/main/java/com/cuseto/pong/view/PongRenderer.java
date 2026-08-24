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

/**
 * Draws a {@link GameSession}'s arena, ball, paddles, and score onto a
 * JavaFX {@link Canvas}.
 *
 * <p>Each call to {@link #render(GameSession)} redraws the entire canvas
 * from scratch: it clears the canvas to black, then draws the arena
 * boundaries, ball, both paddles, and the score line, all based on the
 * session's current state. It holds no game state itself.
 */
public final class PongRenderer {
    private final Canvas canvas;

    /**
     * Creates a renderer with a canvas of the given size.
     *
     * @param width the canvas width, in pixels
     * @param height the canvas height, in pixels
     */
    public PongRenderer(double width, double height) {
        this.canvas = new Canvas(width, height);
    }

    /**
     * Redraws the canvas to reflect the given session's current arena,
     * ball, paddle, and score state.
     *
     * @param gameSession the session to render; must not be {@code null}
     */
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

    /**
     * Returns the JavaFX canvas this renderer draws onto.
     *
     * @return the canvas
     */
    public Canvas canvas() {
        return canvas;
    }

}
