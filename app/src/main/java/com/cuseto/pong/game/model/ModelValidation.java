package com.cuseto.pong.game.model;

final class ModelValidation {
    private ModelValidation() {
    }

    static double requireFinite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }

    static double requirePositiveFinite(String name, double value) {
        requireFinite(name, value);
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
        return value;
    }

    static int requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
        return value;
    }

    static int requireNonNegative(String name, int value) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must not be negative");
        }
        return value;
    }
}
