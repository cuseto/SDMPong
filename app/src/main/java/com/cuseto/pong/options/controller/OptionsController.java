package com.cuseto.pong.options.controller;

import java.util.Objects;

import com.cuseto.pong.config.ConfigRepository;
import com.cuseto.pong.config.MatchSettings;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.view.OptionsPageRenderer;
import com.cuseto.pong.config.schema.controls.ControlsConfig;
import com.cuseto.pong.config.schema.controls.PaddleControlsConfig;

import javafx.scene.Scene;

/**
 * Owns the options screen, validating and saving the match and control
 * settings edited there.
 *
 * <p>On construction, this wires the options screen's back and save
 * buttons to this controller. Saving parses and validates the entered
 * values via {@link MatchSettings} and {@link ControlsConfig}; on success
 * the resulting {@link AppConfig} is persisted via {@link ConfigRepository}
 * and validation feedback is cleared, and on failure the screen shows the
 * validation error instead of saving.
 */
public final class OptionsController {
    private final ConfigRepository configRepository;
    private AppConfig appConfig;
    private final OptionsPageRenderer optionsPageRenderer;

    /**
     * Creates an options controller for the given configuration, rendering
     * the options screen pre-filled with its current values and wiring up
     * the back and save buttons.
     *
     * @param appConfig the configuration to display and save changes against; must not be {@code null}
     * @param configRepository the repository used to persist saved changes; must not be {@code null}
     * @param backToMainMenuAction the callback invoked when the back button is clicked, to leave the options screen
     * @throws NullPointerException if {@code appConfig} or {@code configRepository} is {@code null}
     */
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

    /**
     * Parses, validates, and saves the given match and control settings.
     *
     * <p>Winning score, ball speed, and paddle speed are parsed as numbers
     * and validated by {@link MatchSettings}; the current key bindings are
     * read directly from the options screen and validated by
     * {@link ControlsConfig}. If all values are valid, the resulting
     * configuration is saved via {@link ConfigRepository} and any
     * previously shown validation feedback is cleared. If parsing or
     * validation fails, nothing is saved and the screen shows the
     * validation error instead.
     *
     * @param winningScore the score needed to win, as entered by the player
     * @param ballSpeed the ball's initial speed, as entered by the player
     * @param paddleSpeed the paddles' movement speed, as entered by the player
     * @param ballSpeedIncreaseEnabled whether ball speed should increase on paddle bounces
     */
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

    /**
     * Returns the JavaFX scene for the options screen, for attaching to
     * the application window.
     *
     * @return the options screen scene
     */
    public Scene scene() {
        return optionsPageRenderer.scene();
    }
}
