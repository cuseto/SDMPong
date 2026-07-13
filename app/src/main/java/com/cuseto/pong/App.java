package com.cuseto.pong;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        final int WIDTH = 800;
        final int HEIGHT = 600;

        StackPane stack = new StackPane();
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Label label = new Label("Pong");
        label.setStyle("-fx-text-fill: white; " +
               "-fx-padding: 10px; " +
               "-fx-font-size: 20px;");
        
        root.getChildren().addAll(canvas, label);
        stack.getChildren().add(root);
        
        Scene scene = new Scene(stack, WIDTH, HEIGHT);
        stage.setTitle("Pong");
        stage.setScene(scene);
        stage.show();


    }
}
