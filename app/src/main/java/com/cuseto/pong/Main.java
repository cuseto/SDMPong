package com.cuseto.pong;

/**
 * Standalone launcher for {@link App}.
 */
public class Main {
    /**
     * Creates a new launcher instance.
     */
    public Main() {
    }
    
    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments, passed through to {@link App}
     */
    public static void main(String[] args) {
        App.launch(App.class, args);
    }
}
