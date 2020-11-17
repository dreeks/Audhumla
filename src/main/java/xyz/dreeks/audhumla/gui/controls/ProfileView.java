package xyz.dreeks.audhumla.gui.controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import xyz.dreeks.audhumla.profiles.Profile;

import java.io.IOException;

public class ProfileView extends VBox {

    private Profile profile;

    @FXML
    public TextField nameField;

    @FXML
    public Label installLocationLabel;

    @FXML
    public Label mcVersionLabel;

    @FXML
    public Button saveButton;

    @FXML
    public Button deleteButton;

    public ProfileView() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile_view.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setProfile(null);

        this.nameField.textProperty().addListener((o, old, n) -> {
            this.saveButton.setDisable(n.length() == 0);
        });
    }

    public void setProfile(Profile p) {
        this.profile = p;

        if (p != null) {
            this.nameField.setText(p.profileName);
            this.nameField.setDisable(false);
            this.installLocationLabel.setText(p.installLocation.getAbsolutePath());
            this.mcVersionLabel.setText(p.version.name);

            this.saveButton.setDisable(false);
        } else {
            this.nameField.setText("");
            this.nameField.setDisable(true);
            this.installLocationLabel.setText("None");
            this.mcVersionLabel.setText("None");

            this.saveButton.setDisable(true);
        }
    }

    public void saveProfile() {
        if (this.profile != null) {
            this.profile.save();
        }
    }

    public void deleteProfile() {

    }
}
