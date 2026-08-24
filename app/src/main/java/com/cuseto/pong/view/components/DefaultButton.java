package com.cuseto.pong.view.components;

/**
 * A consistently styled button for the application's user interface.
 */
public class DefaultButton extends StyledButton {
    private static final ButtonAppearance APPEARANCE = new ButtonAppearance(
        200,
        50,
        "#16213e",
        "white",
        "#e94560",
        "#263b6e",
        "white",
        "#ff6b81",
        "#e94560",
        "white",
        "white"
    );

    /**
     * Creates a default-styled button with the given label.
     *
     * @param text the button's label text
     */
    public DefaultButton(String text) {
        super(text, APPEARANCE);
    }
}
