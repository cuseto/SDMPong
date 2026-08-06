package com.cuseto.pong.config.validation;

import java.util.Objects;

public final class ConfigValidation {
    private ConfigValidation() {
    }

    public static void requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
    }

    public static void requireNonNegative(String name, int value) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must not be negative");
        }
    }

    public static void requireFinite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
    }

    public static void requirePositiveFinite(String name, double value) {
        requireFinite(name, value);
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
    }

    public static <T> T requireNonNull(String name, T value) {
        return Objects.requireNonNull(value, name + " must not be null");
    }
}
