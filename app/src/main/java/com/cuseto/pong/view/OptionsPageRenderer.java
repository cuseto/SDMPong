package com.cuseto.pong.view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public final class OptionsPageRenderer {
    private final Scene scene;
    private final VBox root;
    private final String OPTIONS_PAGE_ID = "optionsPage";

    public OptionsPageRenderer(double windowWidth, double windowHeight) {
        root = new VBox(10);
        root.setStyle("-fx-background-color: green;");
        root.setId(OPTIONS_PAGE_ID);
        scene = new Scene(root, windowWidth, windowHeight);
    }

    public Scene scene() {
        return scene;
    }
}