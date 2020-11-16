package xyz.dreeks.audhumla;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import xyz.dreeks.audhumla.gui.controls.ProfileSelector;
import xyz.dreeks.audhumla.exception.ProfileError;
import xyz.dreeks.audhumla.gui.panels.AccountPanel;
import xyz.dreeks.audhumla.model.Account;
import xyz.dreeks.audhumla.model.BindedAccount;
import xyz.dreeks.audhumla.profiles.Profile;

public class MainController {

    private ObservableList<Profile> profiles;

    private BindedAccount bindedAccount;

    @FXML
    private Label username;

    @FXML
    private ProfileSelector profileSelector;

    @FXML
    public TabPane tabPane;

    @FXML
    public Tab tabAccounts;

    @FXML
    public AccountPanel accountPanel;

    public void initialize() {
        try {
            this.profiles = Profile.load();
        } catch (ProfileError profileError) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Corrupted profile found");
            a.setContentText(profileError.getMessage());
            a.showAndWait();

            this.profiles = FXCollections.observableArrayList();
        }

        this.profileSelector.setProfiles(this.profiles);
        this.bindedAccount = new BindedAccount(Main.defaultAccount);

        this.username.textProperty().bind(this.bindedAccount.username);

        this.bindedAccount.id.addListener((o, old, n) -> {
            ((ImageView)this.username.getGraphic()).setImage(new Image(Main.config.getCrafatarURL(this.bindedAccount.id.get())));
        });

        ((ImageView)this.username.getGraphic()).setImage(new Image(Main.config.getCrafatarURL(this.bindedAccount.id.get())));
    }

    public void onClickUsername() {
        this.tabPane.getSelectionModel().select(this.tabAccounts);
    }

    public void setAccount(Account a) {
        this.bindedAccount.setAccount(a);
    }

}
