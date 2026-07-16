package com.cuseto.pong.view;

import com.cuseto.pong.model.Circle;
import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;
import com.cuseto.pong.model.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class PongRenderer {

    public void render(GraphicsContext graphics, GameConfig config, GameState state) {
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, config.screenWidth(), config.screenHeight());

        drawArenaBoundaries(graphics, config);
        drawCircle(graphics, state.ball());
        drawRectangle(graphics, state.leftPaddle());
        drawRectangle(graphics, state.rightPaddle());
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

    private void drawCircle(GraphicsContext graphics, Circle circle) {
        graphics.setFill(Color.WHITE);
        double diameter = 2.0 * circle.radius();
        graphics.fillOval(
            circle.startPosX() - circle.radius(),
            circle.startPosY() - circle.radius(),
            diameter,
            diameter
        );
    }

    private void drawRectangle(GraphicsContext graphics, Rectangle rectangle) {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(
            rectangle.startPosX(), 
            rectangle.startPosY(), 
            rectangle.width(), 
            rectangle.height()
        );
    }

}
