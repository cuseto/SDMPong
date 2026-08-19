package com.cuseto.pong.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public final class OptionsPageRenderer {
    private static final String OPTIONS_PAGE_ID = "optionsPage";
    private static final String BACK_BUTTON_ID = "optionsBackButton";

    private final Scene scene;
    private final VBox root;
    private final Button backButton;

    public OptionsPageRenderer(
        double windowWidth,
        double windowHeight
    ) {
        backButton = new Button("Back");
        backButton.setId(BACK_BUTTON_ID);

        root = new VBox(10);
        root.setStyle("-fx-background-color: green;");
        root.setId(OPTIONS_PAGE_ID);
        root.getChildren().add(backButton);

        scene = new Scene(root, windowWidth, windowHeight);
    }

    public void setClickOnBackButton(Runnable backAction) {
        backButton.setOnAction(event -> backAction.run());
    }

    public Scene scene() {
        return scene;
    }
}
