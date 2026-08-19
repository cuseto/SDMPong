package com.cuseto.pong.options.controller;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.view.OptionsPageRenderer;

import javafx.scene.Scene;

public final class OptionsController {
    private final OptionsPageRenderer optionsPageRenderer;

    public OptionsController(AppConfig appConfig) {
        optionsPageRenderer = new OptionsPageRenderer(
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight()
        );
    }

    public Scene scene() {
        return optionsPageRenderer.scene();
    }
}