package com.cuseto.pong.options.controller;

import java.util.Objects;

import com.cuseto.pong.config.ConfigRepository;
import com.cuseto.pong.config.MatchSettings;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.view.OptionsPageRenderer;

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
                optionsPageRenderer.paddleSpeed()
            )
        );
    }

    public void saveMatchSettings(
        String winningScore,
        String ballSpeed,
        String paddleSpeed
    ) {
        try {
            MatchSettings settings = new MatchSettings(
                Integer.parseInt(winningScore),
                Double.parseDouble(ballSpeed),
                Double.parseDouble(paddleSpeed)
            );

            appConfig = settings.applyTo(appConfig);
            configRepository.save(appConfig);
            optionsPageRenderer.clearValidationFeedback();
        }
        catch (IllegalArgumentException exception) {
            optionsPageRenderer.showValidationFeedback(exception.getMessage());
        }
    }

    public Scene scene() {
        return optionsPageRenderer.scene();
    }
}
