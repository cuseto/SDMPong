package com.cuseto.pong.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArenaTest {
    private static final Arena ARENA = new Arena(1004, 708, 10, 50, 3);

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
