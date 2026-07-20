package com.cuseto.pong.game;

import com.cuseto.pong.model.PaddleDirection;
import javafx.scene.input.KeyCode;

public final class PaddleKeyMapping {

    private PaddleKeyMapping() {
    }

    public static PaddleDirection leftDirectionFor(KeyCode key) {
        if (key == KeyCode.W) {
            return PaddleDirection.UP;
        }
        else if (key == KeyCode.S) {
            return PaddleDirection.DOWN;
        }
        return PaddleDirection.NONE;
    }

    public static PaddleDirection rightDirectionFor(KeyCode key) {
        if (key == KeyCode.UP) {
            return PaddleDirection.UP;
        }
        else if (key == KeyCode.DOWN) {
            return PaddleDirection.DOWN;
        }
        return PaddleDirection.NONE;
    }
}
