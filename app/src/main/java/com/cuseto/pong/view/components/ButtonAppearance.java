package com.cuseto.pong.view.components;

/**
 * Visual configuration for a {@link StyledButton} across its interaction
 * states.
 */
record ButtonAppearance(
    double preferredWidth,
    double preferredHeight,
    String defaultBackgroundColor,
    String defaultTextColor,
    String defaultBorderColor,
    String hoverBackgroundColor,
    String hoverTextColor,
    String hoverBorderColor,
    String activeBackgroundColor,
    String activeTextColor,
    String activeBorderColor
) {
    String defaultStyle() {
        return style(
            defaultBackgroundColor,
            defaultTextColor,
            defaultBorderColor
        );
    }

    String hoverStyle() {
        return style(
            hoverBackgroundColor,
            hoverTextColor,
            hoverBorderColor
        );
    }

    String activeStyle() {
        return style(
            activeBackgroundColor,
            activeTextColor,
            activeBorderColor
        );
    }

    private String style(
        String backgroundColor,
        String textColor,
        String borderColor
    ) {
        return """
            -fx-background-color: %s;
            -fx-text-fill: %s;
            -fx-border-color: %s;
            -fx-border-width: 2;
            -fx-cursor: hand;
            -fx-border-radius: 9;
            -fx-background-radius: 9;
            """.formatted(backgroundColor, textColor, borderColor);
    }
}
