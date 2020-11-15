package xyz.dreeks.audhumla;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import xyz.dreeks.audhumla.config.Configuration;
import xyz.dreeks.audhumla.gui.BorderlessScene;
import xyz.dreeks.audhumla.gui.FrameTab;
import xyz.dreeks.audhumla.manifests.VersionManifest;
import xyz.dreeks.audhumla.os.*;


public class Main extends Application {

    public static final Configuration config = Configuration.load();
    public static final Gson gson = new Gson();
    public static Vibrancy vibrancy;

    public static VersionManifest versionsAvailable;

    private static final double MIN_WIDTH = 510;
    private static final double MIN_HEIGHT = 450;

    @Override
    public void start(Stage stage) throws Exception {
        BorderlessScene bs = new BorderlessScene(stage);
        stage.setTitle(config.launcherTitle);

        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);

        stage.setWidth(850);
        stage.setHeight(500);

        stage.widthProperty().addListener((o, ol, n) -> {
            if (n.doubleValue() < MIN_WIDTH) {
                stage.setWidth(MIN_WIDTH);
            }
        });

        stage.heightProperty().addListener((o, ol, n) -> {
            if (n.doubleValue() < MIN_HEIGHT) {
                stage.setHeight(MIN_HEIGHT);
            }
        });

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        stage.setX((screen.getWidth() - stage.getWidth())/2);
        stage.setY((screen.getHeight() - stage.getHeight())/2);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        FrameTab ft = loader.load();
        MainController controller = loader.getController();

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: transparent");
        bp.setCenter(ft.withTitlebarButtons(bs));
        bs.setMoveControl(controller.tabPane);
        bs.setContent(bp);

        stage.setScene(bs);
        stage.show();

        Main.vibrancy.applyVibrancy(stage);
    }

    public static void main(String[] args) {
        switch (xyz.dreeks.audhumla.os.Utils.getOS()) {
            case OSX:
                Main.vibrancy = new VibrancyOSX();
                break;
            case WINDOWS:
                Main.vibrancy = new VibrancyWindows();
                break;
            case LINUX:
                Main.vibrancy = new VibrancyLinux();
                break;
            default:
                Main.vibrancy = new VibrancyOther();
                break;
        }

        try {
            Main.versionsAvailable = gson.fromJson(Utils.fetchURL(Main.config.versionManifest), VersionManifest.class);
        } catch (Exception e) {
            // @TODO Handle this differently since we want the player to be able to play offline
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Can't download versions!");
            a.setContentText("The launcher could not download available versions!");
            a.showAndWait();
            System.exit(1);
        }

        launch(args);
    }

}
