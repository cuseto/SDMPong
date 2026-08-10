package com.cuseto.pong.config.schema.game;

import com.cuseto.pong.config.validation.ConfigValidation;

public record ArenaConfig(
    int spacingTop,
    int spacingOther,
    int boundaryThickness
) {
    public ArenaConfig {
        ConfigValidation.requireNonNegative("spacingTop", spacingTop);
        ConfigValidation.requireNonNegative("spacingOther", spacingOther);
        ConfigValidation.requirePositive("boundaryThickness", boundaryThickness);
    }
}
