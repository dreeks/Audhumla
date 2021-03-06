package xyz.dreeks.audhumla.gui.controls;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import xyz.dreeks.audhumla.profiles.Profile;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileSelector extends HBox {

    private ObservableList<Profile> profiles;
    public Profile selectedProfile;

    private IntegerProperty selectedProfileIndex = new SimpleIntegerProperty(0);
    private StringProperty selectedProfileName = new SimpleStringProperty("No profiles available !");

    @FXML
    public Label selectedProfileLabel;

    @FXML
    public Button previousButton;

    @FXML
    public Button nextButton;

    public ProfileSelector() {
        super();
        this.profiles = FXCollections.observableArrayList();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile_selector.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.selectedProfileLabel.textProperty().bind(this.selectedProfileName);
        this.previousButton.disableProperty().bind(this.selectedProfileIndex.lessThanOrEqualTo(0));

        this.selectedProfileIndex.addListener((observable, old, val) -> {
            if (val.intValue() == -1 || this.profiles.size() == 0) {
                this.selectedProfileName.set("No profiles available !");
                this.selectedProfile = null;
                return;
            }

            Profile p = this.profiles.get(this.selectedProfileIndex.get());
            this.selectedProfileName.set(p.profileName);
            this.selectedProfile = p;
        });

        this.previousButton.setOnMouseClicked(e -> {
            this.selectedProfileIndex.set(this.selectedProfileIndex.get() - 1);
            this.selectedProfile = this.profiles.get(this.selectedProfileIndex.get());
        });

        this.nextButton.setOnMouseClicked(e -> {
            this.selectedProfileIndex.set(this.selectedProfileIndex.get() + 1);
            this.selectedProfile = this.profiles.get(this.selectedProfileIndex.get());
        });

    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = FXCollections.observableArrayList(profiles);

        IntegerBinding profileListSizeProperty = Bindings.size(this.profiles);
        this.nextButton.disableProperty().bind(this.selectedProfileIndex.greaterThanOrEqualTo(profileListSizeProperty.subtract(1)));

        this.selectedProfileIndex.set(0);
        if (this.selectedProfileIndex.intValue() == -1 || this.profiles.size() == 0) {
            this.selectedProfileName.set("No profiles available !");
            this.selectedProfile = null;
        } else {
            Profile p = this.profiles.get(this.selectedProfileIndex.get());
            this.selectedProfileName.set(p.profileName);
            this.selectedProfile = p;
        }
    }

}
