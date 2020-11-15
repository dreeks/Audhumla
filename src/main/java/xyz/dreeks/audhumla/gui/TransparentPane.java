package xyz.dreeks.audhumla.gui;

import javafx.scene.layout.Pane;

public class TransparentPane extends Pane {

    public TransparentPane() {
        super();
        this.setStyle("-fx-background-color: rgba(255, 255, 255, .7)");
    }

}
