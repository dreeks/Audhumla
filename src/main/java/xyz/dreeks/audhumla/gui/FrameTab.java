package xyz.dreeks.audhumla.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class FrameTab extends TabPane {

    public FrameTab(){
        this.setStyle("-fx-background-color: transparent");
    }

    private static Button createTabButton(String iconName) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(FrameTab.class.getResource(iconName + ".png").toExternalForm(),16, 16, false, true));
        button.setGraphic(imageView);
        button.getStyleClass().add("tab-button");
        return button;
    }

    public AnchorPane withTitlebarButtons(BorderlessScene bs) {
        HBox hbox = new HBox();

        if (bs.isMinimizable()) {
            Button bt = FrameTab.createTabButton("minimize");
            bt.setOnAction((a) -> {
                bs.minimise();
            });
            hbox.getChildren().add(bt);
        }

        if (bs.isMaximizable()) {
            Button bt = FrameTab.createTabButton("maximize");
            bt.setOnAction((a) -> {
                bs.maximise();
            });
            hbox.getChildren().add(bt);
        }

        if (bs.isClosable()) {
            Button bt = FrameTab.createTabButton("close");
            bt.setOnAction((a) -> {
               System.exit(0);
            });
            hbox.getChildren().add(bt);
        }

        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().addAll(this, hbox);
        AnchorPane.setTopAnchor(hbox, 3.0);
        AnchorPane.setRightAnchor(hbox, 5.0);
        AnchorPane.setTopAnchor(this, 1.0);
        AnchorPane.setRightAnchor(this, 1.0);
        AnchorPane.setLeftAnchor(this, 1.0);
        AnchorPane.setBottomAnchor(this, 1.0);

        return anchor;
    }
}
