package com.cuseto.pong.game;

import com.cuseto.pong.model.GameState;
import javafx.animation.AnimationTimer;

import java.util.Objects;
import java.util.function.Consumer;

public final class GameLoop extends AnimationTimer {
    private static final long NO_PREVIOUS_FRAME = -1L;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;

    private final GameUpdater updater;
    private final Consumer<GameState> renderer;

    private GameState currentState;
    private long previousFrameNanos = NO_PREVIOUS_FRAME;

    public GameLoop(GameState initialState, GameUpdater updater, Consumer<GameState> renderer) {
        this.currentState = Objects.requireNonNull(initialState, "initialState cannot be null");
        this.updater = Objects.requireNonNull(updater, "updater cannot be null");
        this.renderer = Objects.requireNonNull(renderer, "renderer cannot be null");
    }

    @Override
    public void handle(long now) {
        tick(now);
    }

    void tick(long now) {
        double elapsedSeconds = elapsedSecondsSincePreviousFrame(now);
        previousFrameNanos = now;

        currentState = Objects.requireNonNull(
            updater.update(currentState, elapsedSeconds),
            "updater cannot return null"
        );
        renderer.accept(currentState);
    }

    public GameState currentState() {
        return currentState;
    }

    /**
     * Helper that calculates the time difference between 
     * the current frame and the previous frame, in seconds
     */
    private double elapsedSecondsSincePreviousFrame(long now) {
        if (previousFrameNanos == NO_PREVIOUS_FRAME) {
            return 0.0;
        }
        return Math.max(0.0, (now - previousFrameNanos) / NANOS_PER_SECOND);
    }
}
