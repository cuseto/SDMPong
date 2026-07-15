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
        GameState state = GameState.initial(GameConfig.standard());

        Canvas canvas = new Canvas(state.arenaWidth(), state.arenaHeight());
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        PongRenderer renderer = new PongRenderer();
        renderer.render(graphics, state);

        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, state.arenaWidth(), state.arenaHeight());
        stage.setTitle("Pong");
        stage.setScene(scene);
        stage.show();

        gameLoop = new GameLoop(
            state,
            (currentState, elapsedSeconds) -> currentState,
            currentState -> renderer.render(graphics, currentState)
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
