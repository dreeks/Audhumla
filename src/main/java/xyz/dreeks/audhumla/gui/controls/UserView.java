package xyz.dreeks.audhumla.gui.controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.gui.panels.AccountPanel;
import xyz.dreeks.audhumla.model.Account;
import xyz.dreeks.audhumla.utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

public class UserView extends StackPane {

    // @TODO: Maybe try to use JavaFX properties binding
    // @TODO: It would require a Property adapter for Gson
    // Or create a AccountProperty kinda thing
    // @see https://github.com/joffrey-bion/fx-gson
    public Account userAccount;

    @FXML
    public Label usernameLabel;

    @FXML
    public Label lastUsedLabel;

    @FXML
    public Label amtGameLaunchLabel;

    @FXML
    public Button deleteButton;

    @FXML
    public Button setAsDefaultButton;

    @FXML
    public ImageView userHead;

    public UserView() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_view.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setUserAccount(null);
    }

    public void setUserAccount(Account a) {
        this.userAccount = a;
        if (a != null) {
            this.usernameLabel.setText(a.username);

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            this.lastUsedLabel.setText(a.lastUsed == null ? "Never" : sdf.format(a.lastUsed));
            this.amtGameLaunchLabel.setText(a.lastUsed == null ? "Never" : ""+a.gameLaunched);

            this.deleteButton.setDisable(false);
            this.setAsDefaultButton.setDisable(a.isDefault());

            this.userHead.setImage(Utils.loadImageForUser(this.userAccount.id));
        } else {
            this.usernameLabel.setText("-");
            this.lastUsedLabel.setText("-");
            this.amtGameLaunchLabel.setText("-");

            this.deleteButton.setDisable(true);
            this.setAsDefaultButton.setDisable(true);

            this.userHead.setImage(Utils.loadImageForUser(null));
        }
    }

    public void setAsDefault() {
        if (this.userAccount != null) {
            try {
                this.setAsDefaultButton.setDisable(true);
                this.userAccount.setDefault();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteAccount() {
        if (this.userAccount != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Removing an account");
            alert.setHeaderText("Removing " + this.userAccount.username);
            alert.setContentText("You are going to remove the account " + this.userAccount.username + " from the launcher. Are you sure ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    AccountPanel ap = ((AccountPanel)this.getParent().getParent());
                    if (this.userAccount.isDefault()) {
                        Main.settings.defaultAccountID = null;
                    }
                    ap.accountList.getItems().remove(this.userAccount);
                    Main.settings.accounts = new ArrayList<>(ap.accountList.getItems());
                    Main.settings.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
