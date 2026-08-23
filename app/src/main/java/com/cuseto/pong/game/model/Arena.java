package com.cuseto.pong.game.model;

/**
 * Represents the rectangular playing field, including its boundary walls.
 *
 * <p>{@code width} and {@code height} describe the arena's full outer
 * dimensions, boundary walls included, anchored at {@code (anchorX, anchorY)}
 * on screen. The playable area is the region <em>inside</em> the walls,
 * exposed via {@link #innerWidth()}, {@link #innerHeight()}, and the
 * {@code inner*Boundary} accessors.
 *
 * @param width the arena's total width, boundary walls included; must be positive, and must leave a positive inner width after both side walls are subtracted
 * @param height the arena's total height, boundary walls included; must be positive, and must leave a positive inner height after both walls are subtracted
 * @param anchorX the x-coordinate of the arena's top-left corner on screen; must not be negative
 * @param anchorY the y-coordinate of the arena's top-left corner on screen; must not be negative
 * @param boundaryThickness the thickness of each boundary wall; must be positive
 */
public record Arena(
    int width,  // includes boundaryThickness
    int height, // includes boundaryThickness
    int anchorX,
    int anchorY,
    int boundaryThickness
) {
    /**
     * Validates the record components.
     *
     * @throws IllegalArgumentException if {@code width}, {@code height}, or {@code boundaryThickness} is not positive; if {@code anchorX} or {@code anchorY} is negative; if the boundaries leave no positive inner width or height; or if the arena's far edge would exceed {@link Integer#MAX_VALUE}
     */
    public Arena {
        ModelValidation.requirePositive("width", width);
        ModelValidation.requirePositive("height", height);
        ModelValidation.requireNonNegative("anchorX", anchorX);
        ModelValidation.requireNonNegative("anchorY", anchorY);
        ModelValidation.requirePositive("boundaryThickness", boundaryThickness);

        if (width <= 2L * boundaryThickness) {
            throw new IllegalArgumentException(
                "width must leave a positive inner width after boundaries"
            );
        }
        if (height <= 2L * boundaryThickness) {
            throw new IllegalArgumentException(
                "height must leave a positive inner height after boundaries"
            );
        }
        if ((long) anchorX + width > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("arena exceeds the maximum horizontal coordinate");
        }
        if ((long) anchorY + height > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("arena exceeds the maximum vertical coordinate");
        }
    }

    /** @return the height of the playable area, i.e. {@code height} minus both boundary walls */
    public int innerHeight() {
        return this.height() - 2 * this.boundaryThickness;
    }

    /** @return the width of the playable area, i.e. {@code width} minus both boundary walls */
    public int innerWidth() {
        return this.width() - 2 * this.boundaryThickness;
    }

    /** @return the y-coordinate of the top edge of the playable area, i.e. just inside the top wall */
    public int innerTopBoundary() {
        return this.anchorY + this.boundaryThickness;
    }

    /** @return the x-coordinate of the left edge of the playable area, i.e. just inside the left wall */
    public int innerLeftBoundary() {
        return this.anchorX + this.boundaryThickness;
    }

    /** @return the y-coordinate of the bottom edge of the playable area, i.e. just inside the bottom wall */
    public int innerBottomBoundary() {
        return this.innerTopBoundary() + this.innerHeight(); 
    }

    /** @return the x-coordinate of the right edge of the playable area, i.e. just inside the right wall */
    public int innerRightBoundary() {
        return this.innerLeftBoundary() + this.innerWidth();
    }
}
