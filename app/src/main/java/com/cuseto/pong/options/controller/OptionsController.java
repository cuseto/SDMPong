package com.cuseto.pong.options.controller;

import java.util.Objects;

import com.cuseto.pong.config.ConfigRepository;
import com.cuseto.pong.config.MatchSettings;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.view.OptionsPageRenderer;
import com.cuseto.pong.config.schema.controls.ControlsConfig;
import com.cuseto.pong.config.schema.controls.PaddleControlsConfig;

import javafx.scene.Scene;

public final class OptionsController {
    private final ConfigRepository configRepository;
    private AppConfig appConfig;
    private final OptionsPageRenderer optionsPageRenderer;

    public OptionsController(
        AppConfig appConfig,
        ConfigRepository configRepository,
        Runnable backToMainMenuAction
    ) {
        this.appConfig = Objects.requireNonNull(appConfig, "appConfig cannot be null");
        this.configRepository = Objects.requireNonNull(configRepository, "configRepository cannot be null");

        optionsPageRenderer = new OptionsPageRenderer(
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight(),
            appConfig
        );

        optionsPageRenderer.setClickOnBackButton(backToMainMenuAction);
        optionsPageRenderer.setClickOnSaveButton(() ->
            saveMatchSettings(
                optionsPageRenderer.winningScore(),
                optionsPageRenderer.ballSpeed(),
                optionsPageRenderer.paddleSpeed(),
                optionsPageRenderer.ballSpeedIncreaseEnabled()
            )
        );
    }

    public void saveMatchSettings(
        String winningScore,
        String ballSpeed,
        String paddleSpeed,
        boolean ballSpeedIncreaseEnabled
    ) {
        try {
            MatchSettings settings = new MatchSettings(
                parseInteger(winningScore, "Winning score"),
                parseDouble(ballSpeed, "Ball speed"),
                parseDouble(paddleSpeed, "Paddle speed"),
                ballSpeedIncreaseEnabled
            );

            ControlsConfig controls = new ControlsConfig(
                new PaddleControlsConfig(
                    optionsPageRenderer.leftPaddleUpKey(),
                    optionsPageRenderer.leftPaddleDownKey()
                ),
                new PaddleControlsConfig(
                    optionsPageRenderer.rightPaddleUpKey(),
                    optionsPageRenderer.rightPaddleDownKey()
                )
            );


            appConfig = settings.applyTo(appConfig);
            appConfig = new AppConfig(
                appConfig.viewport(),
                appConfig.gamePage(),
                controls
            );
            configRepository.save(appConfig);
            optionsPageRenderer.clearValidationFeedback();
        }
        catch (IllegalArgumentException exception) {
            optionsPageRenderer.showValidationFeedback(exception.getMessage());
        }
    }

    private int parseInteger(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }

        try {
            return Integer.parseInt(value.trim());
        }
        catch (NumberFormatException exception) {
            throw new IllegalArgumentException(fieldName + " must be a whole number");
        }
    }

    private double parseDouble(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }

        try {
            return Double.parseDouble(value.trim());
        }
        catch (NumberFormatException exception) {
            throw new IllegalArgumentException(fieldName + " must be a valid number");
        }
    }

    public Scene scene() {
        return optionsPageRenderer.scene();
    }
}
