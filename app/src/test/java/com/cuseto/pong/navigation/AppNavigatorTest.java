package com.cuseto.pong.navigation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.schema.AppConfig;
import javafx.stage.Stage;

class AppNavigatorTest extends ApplicationTest {
    private AppConfig appConfig;
    private AppNavigator navigator;

    @Override
    public void start(Stage stage) {
        appConfig = ConfigLoader.load("/test-config.yaml");
        navigator = new AppNavigator(stage, appConfig);
        navigator.start();
    }

    @AfterEach
    void stopNavigator() {
        interact(navigator::stop);
    }

    @Test
    void applicationStartsAtMainMenu() {
        assertEquals(ApplicationScreen.MAIN_MENU, navigator.currentScreen());
        assertEquals(GameplayOverlay.NONE, navigator.gameplayOverlay());
    }
}
