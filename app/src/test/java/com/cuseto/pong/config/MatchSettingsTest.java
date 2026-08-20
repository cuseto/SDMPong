package com.cuseto.pong.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class MatchSettingsTest {
    @Test
    void ballSpeedMustBePositiveAndFinite() {
        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 0.0, 300.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, -1.0, 300.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, Double.NaN, 300.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, Double.POSITIVE_INFINITY, 300.0));
    }

    @Test
    void paddleSpeedMustBePositiveAndFinite() {
        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 300.0, 0.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 300.0, -1.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 300.0, Double.NaN));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 300.0, Double.POSITIVE_INFINITY));
    }

    @Test
    void winningScoreMustBePositive() {
        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(0, 300.0, 300.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(-1, 300.0, 300.0));
    }
}
