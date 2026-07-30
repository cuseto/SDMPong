package com.cuseto.pong.config.model;

import java.util.Objects;

final class ConfigValidation {

    static void requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
    }

    static void requireNonNegative(String name, int value) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must not be negative");
        }
    }

    static void requireFinite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
    }

    static void requirePositiveFinite(String name, double value) {
        requireFinite(name, value);
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
    }

    static <T> T requireNonNull(String name, T value) {
        return Objects.requireNonNull(value, name + " must not be null");
    }
}
