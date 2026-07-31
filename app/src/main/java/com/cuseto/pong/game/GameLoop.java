package com.cuseto.pong.game;

import java.util.Objects;
import java.util.function.Consumer;

import javafx.animation.AnimationTimer;

public final class GameLoop extends AnimationTimer {
    private static final long NO_PREVIOUS_FRAME = -1L;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;

    private final GameUpdater updater;
    private final Consumer<GameSession> renderer;

    private final GameSession currentState;
    private long previousFrameNanos = NO_PREVIOUS_FRAME;

    public GameLoop(GameSession initialState, GameUpdater updater, Consumer<GameSession> renderer) {
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

        updater.update(currentState, elapsedSeconds);
        renderer.accept(currentState);
    }

    public GameSession currentState() {
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
