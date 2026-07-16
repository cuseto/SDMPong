package com.cuseto.pong;

import com.cuseto.pong.game.GameLoop;
import com.cuseto.pong.model.GameConfig;
import com.cuseto.pong.model.GameState;
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
        stage.show();

        gameLoop = new GameLoop(
            state,
            (currentState, elapsedSeconds) -> currentState,
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
