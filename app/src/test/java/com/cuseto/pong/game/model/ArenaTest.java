package com.cuseto.pong.game.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArenaTest {
    private static final Arena ARENA = new Arena(1004, 708, 10, 50, 3);

    @Test
    void constructorRejectsInvalidDimensionsAnchorsAndBoundaryThickness() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Arena(0, 708, 10, 50, 3)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Arena(1004, -1, 10, 50, 3)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Arena(1004, 708, -1, 50, 3)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Arena(1004, 708, 10, -1, 3)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Arena(1004, 708, 10, 50, 0)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Arena(6, 708, 10, 50, 3)),
            () -> assertThrows(IllegalArgumentException.class,
                () -> new Arena(1004, 6, 10, 50, 3))
        );
    }

    @Test
    void innerDimensionsExcludeBoundaryThicknessOnBothSides() {
        assertAll(
            () -> assertEquals(998, ARENA.innerWidth()),
            () -> assertEquals(702, ARENA.innerHeight())
        );
    }

    @Test
    void innerBoundariesIncludeAnchorAndBoundaryThickness() {
        assertAll(
            () -> assertEquals(13, ARENA.innerLeftBoundary()),
            () -> assertEquals(1011, ARENA.innerRightBoundary()),
            () -> assertEquals(53, ARENA.innerTopBoundary()),
            () -> assertEquals(755, ARENA.innerBottomBoundary())
        );
    }
}
