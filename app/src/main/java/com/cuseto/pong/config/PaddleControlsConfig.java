package com.cuseto.pong.config;

import javafx.scene.input.KeyCode;

public record PaddleControlsConfig(
    KeyCode up,
    KeyCode down
) {}
