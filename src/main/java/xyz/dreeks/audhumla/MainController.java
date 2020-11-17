package xyz.dreeks.audhumla;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import xyz.dreeks.audhumla.gui.controls.ProfileSelector;
import xyz.dreeks.audhumla.gui.panels.AccountPanel;
import xyz.dreeks.audhumla.model.Account;
import xyz.dreeks.audhumla.model.BindedAccount;
import xyz.dreeks.audhumla.utils.Utils;

public class MainController {

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
        this.profileSelector.setProfiles(Main.profiles);
        Main.profileObserver.add(o -> MainController.this.profileSelector.setProfiles(Main.profiles));
        this.bindedAccount = new BindedAccount(Main.defaultAccount);

        this.username.textProperty().bind(this.bindedAccount.username);

        this.bindedAccount.id.addListener((o, old, n) -> {
            ((ImageView)this.username.getGraphic()).setImage(Utils.loadImageForUser(this.bindedAccount.id.get()));
        });

        ((ImageView)this.username.getGraphic()).setImage(Utils.loadImageForUser(this.bindedAccount.id.get()));
    }

    public void onClickUsername() {
        this.tabPane.getSelectionModel().select(this.tabAccounts);
    }

    public void setAccount(Account a) {
        this.bindedAccount.setAccount(a);
    }

}
