package com.cuseto.pong;

import com.cuseto.pong.config.ConfigLoader;
import com.cuseto.pong.config.model.AppConfig;
import com.cuseto.pong.game.BallGameUpdater;
import com.cuseto.pong.game.GameLoop;
import com.cuseto.pong.game.GameSession;
import com.cuseto.pong.game.PaddleGameUpdater;
import com.cuseto.pong.game.PaddleInputState;
import com.cuseto.pong.game.PaddleKeyMapping;
import com.cuseto.pong.model.PaddleDirection;
import com.cuseto.pong.view.PongRenderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    private GameLoop gameLoop;

    @Override
    public void start(Stage stage) {
        // getting configs
        AppConfig appConfig = ConfigLoader.load();
        GameSession gameSession = new GameSession(appConfig);

        // rendering the page
        Canvas canvas = new Canvas(appConfig.viewport().screenWidth(), appConfig.viewport().screenHeight());
        PongRenderer renderer = new PongRenderer();
        renderer.render(canvas, gameSession);

        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(
            root,
            appConfig.viewport().screenWidth(),
            appConfig.viewport().screenHeight()
        );
        stage.setTitle("Pong");
        stage.setScene(scene);

        // enabling key controls for the paddles
        PaddleInputState inputState = new PaddleInputState();
        scene.setOnKeyPressed(event -> {
            PaddleDirection leftDirection = PaddleKeyMapping.leftDirectionFor(event.getCode());
            if (leftDirection != PaddleDirection.NONE) {
                inputState.setLeftDirection(leftDirection);
            }
            PaddleDirection rightDirection = PaddleKeyMapping.rightDirectionFor(event.getCode());
            if (rightDirection != PaddleDirection.NONE) {
                inputState.setRightDirection(rightDirection);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (PaddleKeyMapping.leftDirectionFor(event.getCode()) != PaddleDirection.NONE) {
                inputState.setLeftDirection(PaddleDirection.NONE);
            }
            if (PaddleKeyMapping.rightDirectionFor(event.getCode()) != PaddleDirection.NONE) {
                inputState.setRightDirection(PaddleDirection.NONE);
            }
        });

        stage.show();

        gameLoop = new GameLoop(
            gameSession,
            new PaddleGameUpdater(inputState, gameSession).andThen(new BallGameUpdater()),
            currentState -> renderer.render(canvas, gameSession)
        );
        gameLoop.start();
    }

    @Override
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
