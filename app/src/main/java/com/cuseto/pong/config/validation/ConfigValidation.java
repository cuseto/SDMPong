package com.cuseto.pong.config.validation;

import java.util.Objects;

/**
 * Validation helpers shared by the configuration schema's compact
 * constructors, used to reject invalid values with a consistent,
 * descriptive message.
 */
public final class ConfigValidation {
    private ConfigValidation() {
    }

    /**
     * Verifies that {@code value} is strictly greater than zero.
     *
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @throws IllegalArgumentException if {@code value} is not greater than zero
     */
    public static void requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
    }

    /**
     * Verifies that {@code value} is zero or greater.
     *
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @throws IllegalArgumentException if {@code value} is negative
     */
    public static void requireNonNegative(String name, int value) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must not be negative");
        }
    }

    /**
     * Verifies that {@code value} is finite (not {@code NaN} or infinite).
     *
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @throws IllegalArgumentException if {@code value} is not finite
     */
    public static void requireFinite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
    }

    /**
     * Verifies that {@code value} is finite and strictly greater than zero.
     *
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @throws IllegalArgumentException if {@code value} is not finite, or is not greater than zero
     */
    public static void requirePositiveFinite(String name, double value) {
        requireFinite(name, value);
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
    }

    /**
     * Verifies that {@code value} is not {@code null}.
     *
     * @param <T> the type of the value being checked
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @return {@code value}, unchanged, if it is not {@code null}
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public static <T> T requireNonNull(String name, T value) {
        return Objects.requireNonNull(value, name + " must not be null");
    }
}
