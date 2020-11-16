package xyz.dreeks.audhumla.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BindedAccount {

    public StringProperty id = new SimpleStringProperty();
    public StringProperty username = new SimpleStringProperty();

    public BindedAccount(Account a) {
        setAccount(a);
    }

    public void setAccount(Account a) {
        if (a != null) {
            this.id.set(a.id);
            this.username.set(a.username);
        } else {
            id.set(null);
            username.set("Please add an account");
        }
    }

}
