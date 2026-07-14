package com.cuseto.pong.view;

import com.cuseto.pong.model.GameState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class PongRenderer {

    public void render(GraphicsContext graphics, GameState state) {
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, state.arenaWidth(), state.arenaHeight());
    }
}
