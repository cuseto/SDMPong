package com.cuseto.pong.game.session;

/**
 * The lifecycle state of a {@link GameSession}.
 */
public enum GameStatus {
    /** The match is in progress; updates and rendering advance normally. */
    RUNNING,
    /** The match is temporarily suspended; game updaters should not advance state. */
    PAUSED,
    /** The match has ended because a player reached the winning score. */
    FINISHED
}