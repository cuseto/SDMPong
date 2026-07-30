package com.cuseto.pong.config;

import javafx.scene.input.KeyCode;

public record ControlsConfig(
    KeyCode leftPaddleUp,
    KeyCode leftPaddleDown,
    KeyCode rigthPaddleUp,
    KeyCode rigthPaddleDown
) {}