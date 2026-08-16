package com.cuseto.pong;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import com.cuseto.pong.navigation.AppNavigator;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private AppNavigator navigator;

    @Override
    public void start(Stage stage) {
        AppConfig appConfig = ConfigLoader.load();
        navigator = new AppNavigator(stage, appConfig);
        navigator.start();
    }

    @Override
    public void stop() {
        if (navigator != null) {
            navigator.stop();
        }
    }
}
