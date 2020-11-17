package xyz.dreeks.audhumla.gui.panels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.manifests.ManifestVersion;
import xyz.dreeks.audhumla.profiles.Profile;
import xyz.dreeks.audhumla.utils.Utils;

import java.io.File;
import java.io.IOException;

public class AddProfileDialog extends VBox {

    private Stage stage;
    private ObservableList<AddProfileVersionType> types = FXCollections.observableArrayList();

    @FXML
    public ComboBox<AddProfileVersionType> typeCB;

    @FXML
    public ComboBox<ManifestVersion> versionCB;

    @FXML
    public TextField profileNameTf;

    @FXML
    public Button saveBt;

    @FXML
    public Button cancelBt;

    public AddProfileDialog(Stage s) {
        super();

        this.stage = s;
        s.setTitle("Adding new profile");
        s.initModality(Modality.APPLICATION_MODAL);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_profile.fxml"));
            // Work around for Scenebuilder.
            // https://stackoverflow.com/questions/50482659/scene-builder-nested-custom-nodes
            loader.setClassLoader(getClass().getClassLoader());
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (ManifestVersion mv : Main.versionsAvailable.versions) {
            boolean found = false;
            for (AddProfileVersionType apvt : this.types) {
                if (apvt.name.equalsIgnoreCase(mv.type)) {
                    apvt.versions.add(mv);
                    found = true;
                }
            }

            if (!found) {
                this.types.add(new AddProfileVersionType(mv));
            }
        }

        this.typeCB.setItems(this.types);
        this.typeCB.getSelectionModel().selectedItemProperty().addListener((o, old, n) ->{
            this.validate();
            this.versionCB.setItems(n.versions);
        });

        this.versionCB.getSelectionModel().selectedItemProperty().addListener((o, old, n) -> {
            this.validate();
        });

        this.profileNameTf.textProperty().addListener((o, old, n) -> {
            this.validate();
        });
    }

    private void validate() {
        boolean isValid = true;
        isValid &= Utils.isValidDirectoryName(this.profileNameTf.getText());
        isValid &= this.typeCB.getSelectionModel().getSelectedItem() != null;
        isValid &= this.versionCB.getSelectionModel().getSelectedItem() != null;

        this.saveBt.setDisable(!isValid);
    }

    public void addProfile() {
        try {
            ManifestVersion v = this.versionCB.getValue();

            Profile p = new Profile(this.profileNameTf.getText());
            p.installLocation = new File("instances/" + p.profileName);
            p.version = Utils.fetchVersion(v);
            p.installLocation.mkdirs();

            p.version.save(p);
            p.save();

            Main.profiles.add(p);

            this.stage.hide();
            Main.updateObservers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        this.stage.hide();
    }

    public static void show() {
        Stage s = new Stage();
        s.setResizable(false);
        Parent root = new AddProfileDialog(s);
        Scene sc = new Scene(root);
        s.setScene(sc);
        s.show();
    }
}

class AddProfileVersionType {
    public String name;
    public ObservableList<ManifestVersion> versions = FXCollections.observableArrayList();

    public AddProfileVersionType(ManifestVersion versionToAdd) {
        this.name = versionToAdd.type;
        this.versions.add(versionToAdd);
    }

    @Override
    public String toString() {
        return this.name;
    }
}