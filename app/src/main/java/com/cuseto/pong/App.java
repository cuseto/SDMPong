package com.cuseto.pong;

import com.cuseto.pong.game.BallGameUpdater;
import com.cuseto.pong.game.GameLoop;
import com.cuseto.pong.game.PaddleGameUpdater;
import com.cuseto.pong.game.PaddleInputState;
import com.cuseto.pong.game.PaddleKeyMapping;
import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;
import com.cuseto.pong.model.PaddleDirection;
import com.cuseto.pong.view.PongRenderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    private GameLoop gameLoop;

    @Override
    public void start(Stage stage) {
        GameConfig config = GameConfig.standard();
        GameState state = GameState.initial(config);

        Canvas canvas = new Canvas(config.screenWidth(), config.screenHeight());
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        PongRenderer renderer = new PongRenderer();
        renderer.render(graphics, config, state);

        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, config.screenWidth(), config.screenHeight());
        stage.setTitle("Pong");
        stage.setScene(scene);

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
            state,
            new PaddleGameUpdater(inputState, config).andThen(new BallGameUpdater(config)),
            currentState -> renderer.render(graphics, config, currentState)
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
