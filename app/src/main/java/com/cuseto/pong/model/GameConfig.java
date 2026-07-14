package com.cuseto.pong.model;

public record GameConfig(   // For fixed things/rules
        int arenaWidth,
        int arenaHeight
) {

    public GameConfig {
        requirePositive("arenaWidth", arenaWidth);
        requirePositive("arenaHeight", arenaHeight);
    }

    public static GameConfig standard() {
        return new GameConfig(800, 600);
    }

    private static void requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive");
        }
    }
}
