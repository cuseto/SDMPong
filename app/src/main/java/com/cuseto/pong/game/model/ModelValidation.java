package com.cuseto.pong.game.model;

/**
 * Validation helpers shared by the model package's constructors, used to
 * reject invalid numeric values with a consistent, descriptive message.
 *
 * <p>Package-private: this is an internal utility for the model classes and
 * is not part of the public API, so it will not appear in Javadoc generated
 * with the default (public-only) visibility.
 */
final class ModelValidation {
    private ModelValidation() {
    }

    /**
     * Verifies that {@code value} is finite (not {@code NaN} or infinite).
     *
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @return {@code value}, unchanged, if it is finite
     * @throws IllegalArgumentException if {@code value} is not finite
     */
    static double requireFinite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }

    /**
     * Verifies that {@code value} is finite and strictly greater than zero.
     *
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @return {@code value}, unchanged, if it is finite and positive
     * @throws IllegalArgumentException if {@code value} is not finite, or is not greater than zero
     */
    static double requirePositiveFinite(String name, double value) {
        requireFinite(name, value);
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
        return value;
    }

    /**
     * Verifies that {@code value} is strictly greater than zero.
     *
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @return {@code value}, unchanged, if it is greater than zero
     * @throws IllegalArgumentException if {@code value} is not greater than zero
     */
    static int requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
        return value;
    }

    /**
     * Verifies that {@code value} is zero or greater.
     *
     * @param name the name of the value, used in the exception message
     * @param value the value to check
     * @return {@code value}, unchanged, if it is not negative
     * @throws IllegalArgumentException if {@code value} is negative
     */
    static int requireNonNegative(String name, int value) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must not be negative");
        }
        return value;
    }
}
