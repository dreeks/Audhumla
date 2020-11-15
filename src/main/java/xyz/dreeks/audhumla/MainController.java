package xyz.dreeks.audhumla;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import xyz.dreeks.audhumla.manifests.ManifestVersion;
import xyz.dreeks.audhumla.manifests.Version;
import xyz.dreeks.audhumla.manifests.VersionManifest;

public class MainController {

    @FXML
    private Label username;

    @FXML
    public TabPane tabPane;

    @FXML
    public ListView tmpList;

    public Version selectedVersion;

    public void initialize() {
        tmpList.setItems(FXCollections.observableArrayList(Main.versionsAvailable.versions));

        tmpList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                selectedVersion = Main.gson.fromJson(Utils.fetchURL(((ManifestVersion)newValue).url), Version.class);
                System.out.println(selectedVersion.mainClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
