package com.cuseto.pong.game;

import com.cuseto.pong.model.PaddleDirection;

public class PaddleInputState {
    
    private PaddleDirection leftDirection = PaddleDirection.NONE;
    private PaddleDirection rightDirection = PaddleDirection.NONE;

    public PaddleDirection leftDirection() {
        return leftDirection;
    }

    public PaddleDirection rightDirection() {
        return rightDirection;
    }

    public void setLeftDirection(PaddleDirection direction) {
        this.leftDirection = direction;
    }

    public void setRightDirection(PaddleDirection direction) {
        this.rightDirection = direction;
    }
}
