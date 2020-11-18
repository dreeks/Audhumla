package xyz.dreeks.audhumla.gui.panels;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.auth.Yggdrasil;
import xyz.dreeks.audhumla.auth.YggdrasilAgent;
import xyz.dreeks.audhumla.auth.responses.Error;
import xyz.dreeks.audhumla.gui.controls.UserView;
import xyz.dreeks.audhumla.model.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class AccountPanel extends HBox {

    private ObservableList<Account> accounts;

    @FXML
    public ListView<Account> accountList;

    @FXML
    public UserView user;

    public AccountPanel() {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("account_panel.fxml"));
            // Work around for Scenebuilder.
            // https://stackoverflow.com/questions/50482659/scene-builder-nested-custom-nodes
            loader.setClassLoader(getClass().getClassLoader());
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.accounts = FXCollections.observableArrayList(Main.settings.accounts);
        this.accountList.setItems(this.accounts);

        this.accountList.getSelectionModel().selectedItemProperty().addListener((o, old, n) -> {
            this.user.setUserAccount(n);
        });
    }

    public void addNewAccount() {
        Dialog<Pair<String, String>> newAccountDialog = new Dialog<>();
        newAccountDialog.setTitle("Adding an account");
        newAccountDialog.setHeaderText("Please login using your Minecraft/Mojang account");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        newAccountDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField email= new TextField();
        email.setPromptText("Email:");

        PasswordField password = new PasswordField();
        password.setPromptText("Password:");

        grid.add(new Label("Email: "), 0, 0);
        grid.add(email, 1, 0);

        grid.add(new Label("Password: "), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = newAccountDialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        email.textProperty().addListener((o, old, n) -> {
            loginButton.setDisable(n.trim().isEmpty());
        });

        newAccountDialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> email.requestFocus());

        newAccountDialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(email.getText(), password.getText());
            }

            return null;
        });

        Optional<Pair<String, String>> result = newAccountDialog.showAndWait();
        if (result.isPresent()) {
            Yggdrasil yd = new Yggdrasil(YggdrasilAgent.getMinecraft());
            try {
                var res = yd.authenticate(result.get().getKey(), result.get().getValue());

                // If it exists, we just want to replace it
                // No need for iterator since there is only one and we'll break right after it
                Account previous = null;
                for (Account acc : this.accounts) {
                    if (acc.id == res.selectedProfile.id) {
                        previous = acc;
                        this.accounts.remove(acc);
                        break;
                    }
                }

                if (previous != null) {
                    previous.username = res.selectedProfile.name;
                    previous.token = res.accessToken;
                    previous.clientToken = res.clientToken;
                } else {
                    previous = new Account(res.selectedProfile.id, res.selectedProfile.name, res.accessToken, res.clientToken, null, 0);
                }

                this.accounts.add(previous);
                // We automatically make the first account added the "default account"
                if (this.accounts.size() == 1) {
                    Main.settings.defaultAccountID = previous.id;
                }
                Main.settings.accounts = new ArrayList<>(this.accounts);
                Main.settings.save();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Error error) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle(error.error);
                a.setContentText(error.errorMessage);
                a.showAndWait();
            }
        }
    }
}
