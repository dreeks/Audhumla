package xyz.dreeks.audhumla.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

// Adapted & Updated version of NicolasSenetLarson's library
// https://github.com/NicolasSenetLarson/BorderlessScene
public class BorderlessScene extends Scene {

    private BorderlessController controller;
    private AnchorPane root;
    private Stage primaryStage;

    private boolean canMinimize;
    private boolean canMaximize;
    private boolean canClose;

    /**
     * The constructor.
     * @param primaryStage your stage.
     */
    public BorderlessScene(Stage primaryStage) {
        super(new TransparentPane());
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        this.setFill(Color.TRANSPARENT);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Borderless.fxml"));
            this.root = loader.load();
            this.root.setStyle("-fx-background-color: rgba(0, 0, 0, .8)");
            setRoot(this.root);

            this.controller = loader.getController();
            this.controller.setMainApp(primaryStage);

            this.primaryStage = primaryStage;

            this.getStylesheets().add(BorderlessScene.class.getResource("frametab.css").toExternalForm());
            this.getStylesheets().add(BorderlessScene.class.getResource("Listview.css").toExternalForm());

            this.canMaximize = this.canMinimize = this.canClose = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change the content of the scene.
     * @param content the root Parent of your new content.
     */
    public void setContent(Parent content) {
        this.root.getChildren().remove(0);
        this.root.getChildren().add(0, content);
        AnchorPane.setLeftAnchor(content, Double.valueOf(0.0D));
        AnchorPane.setTopAnchor(content, Double.valueOf(0.0D));
        AnchorPane.setRightAnchor(content, Double.valueOf(0.0D));
        AnchorPane.setBottomAnchor(content, Double.valueOf(0.0D));
    }

    /**
     * Set a node that can be pressed and dragged to move the application around.
     * @param node the node.
     */
    public void setMoveControl(Node node) {
        this.controller.setMoveControl(node);
    }

    /**
     *  Toggle to maximise the application.
     */
    public void maximise() {
        controller.maximise();
    }

    /**
     * Minimise the application to the taskbar.
     */
    public void minimise() {
        controller.minimise();
    }

    /**
     * Disable/enable the resizing of your application. Enabled by default.
     * @param bool false to disable, true to enable.
     */
    public void setResizable(Boolean bool) {
        controller.setResizable(bool);
    }

    /**
     * Check the maximised state of the application.
     * @return true if the window is maximised.
     */
    public Boolean isMaximised() {
        return controller.maximised;
    }

    /**
     * Returns the width and height of the application when windowed.
     * @return instance of Delta class. Delta.x = width, Delta.y = height.
     */
    public Delta getWindowedSize() {
        if (controller.prevSize.x == null)
            controller.prevSize.x = primaryStage.getWidth();
        if (controller.prevSize.y == null)
            controller.prevSize.y = primaryStage.getHeight();
        return controller.prevSize;
    }

    /**
     * Returns the x and y position of the application when windowed.
     * @return instance of Delta class. Use Delta.x and Delta.y.
     */
    public Delta getWindowedPositon() {
        if (controller.prevPos.x == null)
            controller.prevPos.x = primaryStage.getX();
        if (controller.prevPos.y == null)
            controller.prevPos.y = primaryStage.getY();
        return controller.prevPos;
    }

    public BorderlessScene setMinimizable(boolean b) {
        this.canMinimize = b;
        return this;
    }

    public BorderlessScene setMaximizable(boolean b) {
        this.canMaximize = b;
        return this;
    }

    public BorderlessScene setClosable(boolean b) {
        this.canClose = b;
        return this;
    }

    public boolean isMinimizable() {
        return this.canMinimize;
    }

    public boolean isMaximizable() {
        return this.canMaximize;
    }

    public boolean isClosable() {
        return this.canClose;
    }

}