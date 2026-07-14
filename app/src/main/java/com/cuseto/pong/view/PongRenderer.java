package com.cuseto.pong.view;

import com.cuseto.pong.model.Circle;
import com.cuseto.pong.model.GameState;
import com.cuseto.pong.model.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class PongRenderer {

    public void render(GraphicsContext graphics, GameState state) {
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, state.arenaWidth(), state.arenaHeight());

        drawCircle(graphics, state.ball());
        drawRectangle(graphics, state.leftPaddle());
        drawRectangle(graphics, state.rightPaddle());
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
