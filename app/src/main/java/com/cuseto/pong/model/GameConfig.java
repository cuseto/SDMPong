package com.cuseto.pong.model;

public record GameConfig(   // For fixed things/rules
    int screenWidth,
    int screenHeight,

    int arenaWidth,
    int arenaHeight,
    int arenaSpacingTop,
    int arenaSpacingOther,
    int arenaBoundaryThickness,

    int ballRadius,
    int paddleInset,
    int paddleWidth,
    int paddleHeight
) {

    public GameConfig {
        requirePositive("arenaWidth", arenaWidth);
        requirePositive("arenaHeight", arenaHeight);
    }

    public static GameConfig standard() {
        int screenWidth = 800;
        int screenHeight = 600;

        int arenaSpacingTop = 80;
        int arenaSpacingOther = 20;
        int arenaBoundaryThickness = 4;
        int arenaWidth = screenWidth - 2*arenaSpacingOther;
        int arenaHeight = screenHeight - arenaSpacingTop - arenaSpacingOther;

        return new GameConfig(
            screenWidth, screenHeight, 
            arenaWidth, arenaHeight, 
            arenaSpacingTop, arenaSpacingOther, arenaBoundaryThickness, 
            8, 100, 10, 80
        );
    }

    private static void requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive");
        }
    }
}
