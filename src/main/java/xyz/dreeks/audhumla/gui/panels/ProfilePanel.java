package xyz.dreeks.audhumla.gui.panels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.utils.Utils;
import xyz.dreeks.audhumla.gui.controls.ProfileView;
import xyz.dreeks.audhumla.manifests.ManifestVersion;
import xyz.dreeks.audhumla.profiles.Profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfilePanel extends HBox {

    private ObservableList<Profile> profiles;

    @FXML
    public ListView<Profile> profileList;

    @FXML
    public ProfileView profile;

    public ProfilePanel() {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile_panel.fxml"));
            // Work around for Scenebuilder.
            // https://stackoverflow.com/questions/50482659/scene-builder-nested-custom-nodes
            loader.setClassLoader(getClass().getClassLoader());
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.profiles = FXCollections.observableArrayList(Main.profiles);
        this.profileList.setItems(this.profiles);

        this.profileList.getSelectionModel().selectedItemProperty().addListener((o, old, n) -> {
            this.profile.setProfile(n);
        });

        // Yeah i know JFX is not made to do weird things like this but meh
        // @TODO: use javafx as intended
        Main.profileObserver.add((o) -> {
            this.profileList.setItems(FXCollections.observableArrayList(Main.profiles));
        });
    }

    public void addNewProfile() {
        AddProfileDialog.show();
    }
}
