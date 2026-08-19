package com.cuseto.pong.view;

import javafx.scene.layout.VBox;

public final class GameMenuRenderer {
    private final String MENU_ID = "gameMenu";
    private final VBox root;

    public GameMenuRenderer() {
        this.root = new VBox(10);
        this.root.setStyle("-fx-background-color: blue;");
        this.root.setId(MENU_ID);
    }

    public void render() {
        // TODO: to implement
    }

    public VBox root() {
        return root;
    }
}