package com.cuseto.pong.model;

public record GameConfig(   // For fixed things/rules
    int screenWidth,
    int screenHeight,
    int arenaSpacingTop,
    int arenaSpacingOther,
    int arenaBoundaryThickness,

    int ballRadius,
    int paddleInset,
    int paddleWidth,
    int paddleHeight,
    double paddleSpeed, // double because pixels per second
    double ballVelocityX,
    double ballVelocityY
) {

    public GameConfig {
        requirePositive("screenWidth", screenWidth);
        requirePositive("screenHeight", screenHeight);
        requirePositive("arenaSpacingTop", arenaSpacingTop);
        requirePositive("arenaSpacingOther", arenaSpacingOther);
        requirePositive("arenaBoundaryThickness", arenaBoundaryThickness);
        requirePositive("ballRadius", ballRadius);
        requirePositive("paddleInset", paddleInset);
        requirePositive("paddleWidth", paddleWidth);
        requirePositive("paddleHeight", paddleHeight);

        if (screenWidth - 2 * arenaSpacingOther <= 0) {
            throw new IllegalArgumentException("arenaWidth must be positive");
        }
        if (screenHeight - arenaSpacingTop - arenaSpacingOther <= 0) {
            throw new IllegalArgumentException("arenaHeight must be positive");
        }
    }

    public int arenaWidth() {
        return screenWidth - 2 * arenaSpacingOther;
    }

    public int arenaHeight() {
        return screenHeight - arenaSpacingTop - arenaSpacingOther;
    }

    public int arenaLeft() {
        return arenaSpacingOther + arenaBoundaryThickness;
    }

    public int arenaTop() {
        return arenaSpacingTop + arenaBoundaryThickness;
    }

    public int arenaRight() {
        return arenaSpacingOther + arenaWidth() - arenaBoundaryThickness;
    }

    public int arenaBottom() {
        return arenaSpacingTop + arenaHeight() - arenaBoundaryThickness;
    }

    /**
     * Standard game configuration, with the arena spaced 80px from the top
     * and 20px from the other sides
     */
    public static GameConfig standard() {
        return new GameConfig(
            800, 600, 
            80, 20, 4, 
            8, 100, 10, 80,
            300.0,
            20.0, 140.0
        );
    }

    private static void requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive");
        }
    }
}
