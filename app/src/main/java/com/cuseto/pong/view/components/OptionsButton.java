package com.cuseto.pong.view.components;

/**
 * A consistently styled button for the application's Options menu.
 */
public class OptionsButton extends StyledButton {
    private static final ButtonAppearance APPEARANCE = new ButtonAppearance(
        100,
        50,
        "#33353a",
        "white",
        "#f8f6f6",
        "#4a4d55",
        "white",
        "#e94560",
        "#f8f6f6",
        "#33353a",
        "#e94560"
    );

    /**
     * Creates an options-styled button with the given label.
     *
     * @param text the button's label text
     */
    public OptionsButton(String text) {
        super(text, APPEARANCE);
    }
}
