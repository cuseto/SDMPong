package com.cuseto.pong;

import com.cuseto.pong.config.ConfigRepository;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.navigation.AppNavigator;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX application entry point. Loads configuration and delegates all
 * screen navigation to an {@link AppNavigator} for the lifetime of the
 * application.
 */
public class App extends Application {
    private AppNavigator navigator;

    /**
     * Loads configuration, creates the {@link AppNavigator} for the given
     * stage, and shows the main menu.
     *
     * @param stage the primary stage supplied by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        ConfigRepository configRepository = new ConfigRepository();
        AppConfig appConfig = configRepository.load();

        navigator = new AppNavigator(stage, appConfig, configRepository);
        navigator.start();
    }

    /**
     * Stops any active gameplay session via the {@link AppNavigator},
     * if the application reached {@link #start(Stage)}.
     */
    @Override
    public void stop() {
        if (navigator != null) {
            navigator.stop();
        }
    }
}
