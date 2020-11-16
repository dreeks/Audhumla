package xyz.dreeks.audhumla.model;

import xyz.dreeks.audhumla.Main;

import java.io.IOException;
import java.util.Date;

public class Account {

    public String id;
    public String username;
    public String token;
    public String clientToken;
    public Date lastUsed;
    public int gameLaunched;

    public Account() {}

    public Account(String id, String username, String token, String clientToken, Date lastUsed, int gameLaunched){
        this.id = id;
        this.username = username;
        this.token = token;
        this.clientToken = clientToken;
        this.lastUsed = lastUsed;
        this.gameLaunched = gameLaunched;
    }

    public boolean isDefault() {
        if (Main.settings.defaultAccountID == null)
            return false;
        return this.id.equalsIgnoreCase(Main.settings.defaultAccountID);
    }

    public void setDefault() throws IOException {
        Main.settings.defaultAccountID = this.id;
        Main.defaultAccount = this;
        Main.settings.save();
    }

    @Override
    public String toString() {
        return this.username;
    }
}
