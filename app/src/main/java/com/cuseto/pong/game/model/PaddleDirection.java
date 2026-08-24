package com.cuseto.pong.game.model;

/**
 * The direction a paddle is currently moving in, as driven by player input.
 */
public enum PaddleDirection {
    /** The paddle is moving upward, toward decreasing y. */
    UP,
    /** The paddle is moving downward, toward increasing y. */
    DOWN,
    /** The paddle is stationary; no directional input is active. */
    NONE
}
