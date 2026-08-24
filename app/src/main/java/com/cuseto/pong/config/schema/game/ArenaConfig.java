package com.cuseto.pong.config.schema.game;

import com.cuseto.pong.config.validation.ConfigValidation;

/**
 * The configurable spacing and wall thickness used to size the in-game
 * {@link com.cuseto.pong.game.model.Arena}.
 *
 * @param spacingTop the gap, in pixels, between the top of the screen and the arena; must not be negative
 * @param spacingOther the gap, in pixels, between the arena and the screen's left, right, and bottom edges; must not be negative
 * @param boundaryThickness the thickness of the arena's boundary walls, in pixels; must be positive
 */
public record ArenaConfig(
    int spacingTop,
    int spacingOther,
    int boundaryThickness
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code spacingTop} or {@code spacingOther} is negative, or if {@code boundaryThickness} is not positive
     */
    public ArenaConfig {
        ConfigValidation.requireNonNegative("spacingTop", spacingTop);
        ConfigValidation.requireNonNegative("spacingOther", spacingOther);
        ConfigValidation.requirePositive("boundaryThickness", boundaryThickness);
    }
}
