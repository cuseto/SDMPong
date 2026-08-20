package com.cuseto.pong.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.game.session.GameSession;

public class MatchSettingsTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void ballSpeedMustBePositiveAndFinite() {
        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 0.0, 300.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, -1.0, 300.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, Double.NaN, 300.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, Double.POSITIVE_INFINITY, 300.0));
    }

    @Test
    void paddleSpeedMustBePositiveAndFinite() {
        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 300.0, 0.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 300.0, -1.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 300.0, Double.NaN));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(5, 300.0, Double.POSITIVE_INFINITY));
    }

    @Test
    void winningScoreMustBePositive() {
        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(0, 300.0, 300.0));

        assertThrows(IllegalArgumentException.class,
            () -> new MatchSettings(-1, 300.0, 300.0));
    }

    @Test
    void matchSettingsCanBeAppliedToGameConfiguration() {
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        MatchSettings settings = new MatchSettings(
            10,
            500.0,
            400.0
        );

        AppConfig updatedConfig = settings.applyTo(config);

        assertEquals(10, updatedConfig.gamePage().winningScore());
        assertEquals(500.0, updatedConfig.gamePage().ball().initialVelocityX());
        assertEquals(500.0, updatedConfig.gamePage().ball().initialVelocityY());
        assertEquals(400.0, updatedConfig.gamePage().paddle().speed());
    }

    @Test
    void validMatchSettingsCanBeSavedThroughConfigRepository() {
        Path userConfigPath = temporaryDirectory.resolve("config.yaml");
        ConfigRepository repository = new ConfigRepository(userConfigPath);

        AppConfig config = ConfigLoader.load("/test-config.yaml");

        MatchSettings settings = new MatchSettings(
            10,
            500.0,
            400.0
        );

        repository.save(settings.applyTo(config));

        AppConfig savedConfig = repository.load();

        assertEquals(10, savedConfig.gamePage().winningScore());
        assertEquals(500.0, savedConfig.gamePage().ball().initialVelocityX());
        assertEquals(500.0, savedConfig.gamePage().ball().initialVelocityY());
        assertEquals(400.0, savedConfig.gamePage().paddle().speed());
    }

    @Test
    void applyingMatchSettingsDoesNotMutateAnActiveMatch() {
        AppConfig config = ConfigLoader.load("/test-config.yaml");

        GameSession gameSession = new GameSession(config);

        int originalWinningScore = gameSession.winningScore();
        double originalBallVelocityX = gameSession.ball().velocityX();
        double originalBallVelocityY = gameSession.ball().velocityY();
        double originalLeftPaddleSpeed = gameSession.leftPaddle().speed();
        double originalRightPaddleSpeed = gameSession.rightPaddle().speed();

        MatchSettings settings = new MatchSettings(
            10,
            500.0,
            400.0
        );

        settings.applyTo(config);

        assertEquals(originalWinningScore, gameSession.winningScore());
        assertEquals(originalBallVelocityX, gameSession.ball().velocityX());
        assertEquals(originalBallVelocityY, gameSession.ball().velocityY());
        assertEquals(originalLeftPaddleSpeed, gameSession.leftPaddle().speed());
        assertEquals(originalRightPaddleSpeed, gameSession.rightPaddle().speed());
    }
}
