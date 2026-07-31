package com.cuseto.pong.model;

public record Arena(
    int width,  // includes boundaryThickness
    int height, // includes boundaryThickness
    int anchorX,
    int anchorY,
    int boundaryThickness
) {
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