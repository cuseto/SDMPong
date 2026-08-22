package com.cuseto.pong.game.model;

public record Arena(
    int width,  // includes boundaryThickness
    int height, // includes boundaryThickness
    int anchorX,
    int anchorY,
    int boundaryThickness
) {
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

    public int innerHeight() {
        return this.height() - 2 * this.boundaryThickness;
    }

    public int innerWidth() {
        return this.width() - 2 * this.boundaryThickness;
    }

    public int innerTopBoundary() {
        return this.anchorY + this.boundaryThickness;
    }

    public int innerLeftBoundary() {
        return this.anchorX + this.boundaryThickness;
    }

    public int innerBottomBoundary() {
        return this.innerTopBoundary() + this.innerHeight(); 
    }

    public int innerRightBoundary() {
        return this.innerLeftBoundary() + this.innerWidth();
    }
}
