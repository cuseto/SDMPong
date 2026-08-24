package com.cuseto.pong.game.loop;

import java.util.Objects;
import java.util.function.Consumer;

import com.cuseto.pong.game.session.GameSession;
import com.cuseto.pong.game.update.GameUpdater;

import javafx.animation.AnimationTimer;

/**
 * Drives the game's per-frame loop: on every JavaFX animation frame, it
 * updates the {@link GameSession} state and then renders it.
 *
 * <p>Extends {@link AnimationTimer}, so {@link #start()} (inherited)
 * begins the loop and {@link #stop()} (inherited) halts it; JavaFX invokes
 * {@link #handle(long)} once per frame while running.
 */
public final class GameLoop extends AnimationTimer {
    private static final long NO_PREVIOUS_FRAME = -1L;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;

    private final GameUpdater updater;
    private final Consumer<GameSession> renderer;

    private final GameSession currentState;
    private long previousFrameNanos = NO_PREVIOUS_FRAME;

    /**
     * Creates a game loop that updates and renders the specified game session
     * once per frame.
     *
     * @param initialState the game session this loop will update and render every frame; must not be {@code null}
     * @param updater the update logic applied to {@code initialState} every frame; must not be {@code null}
     * @param renderer the callback invoked with {@code initialState} every frame, after updating; must not be {@code null}
     * @throws NullPointerException if any argument is {@code null}
     */
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

    /**
     * Returns the game session this loop is updating and rendering.
     *
     * @return the game session this loop is updating and rendering
     */
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
