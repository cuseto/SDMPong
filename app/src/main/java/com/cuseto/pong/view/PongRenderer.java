package com.cuseto.pong.view;

import com.cuseto.pong.model.Ball;
import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;
import com.cuseto.pong.model.Paddle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class PongRenderer {

    public void render(GraphicsContext graphics, GameConfig config, GameState state) {
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, config.screenWidth(), config.screenHeight());

        drawArenaBoundaries(graphics, config);
        drawBall(graphics, state.ball());
        drawPaddle(graphics, state.leftPaddle());
        drawPaddle(graphics, state.rightPaddle());
    }

    private void drawArenaBoundaries(GraphicsContext graphics, GameConfig config) {
        graphics.setStroke(Color.WHITE);
        graphics.setLineWidth(config.arenaBoundaryThickness());

        graphics.strokeRect(
            config.arenaSpacingOther(),
            config.arenaSpacingTop(),
            config.arenaWidth(),
            config.arenaHeight()
        );    
    }

    private void drawBall(GraphicsContext graphics, Ball ball) {
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
