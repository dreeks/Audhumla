package xyz.dreeks.audhumla.config;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.model.Account;

import java.io.*;
import java.util.ArrayList;

public class Settings {

    @SerializedName("accounts")
    public ArrayList<Account> accounts = new ArrayList<>();

    @SerializedName("default_account_id")
    public String defaultAccountID;

    public static Settings load() {
        Gson gson = new Gson();
        Settings s = new Settings();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("settings.json")));
            String str;
            String all = "";
            while ((str = br.readLine()) != null) {
                all += str;
            }
            br.close();

            s = gson.fromJson(all, Settings.class);
        } catch (Exception e) {
            System.out.println("No settings found or invalid file");
            e.printStackTrace();
        }

        for (Account a : s.accounts) {
            if (a.id.equalsIgnoreCase(s.defaultAccountID)) { // Can't use isDefault as the Main settings is not initialized yet
                Main.defaultAccount = a;
            }
        }

        return s;
    }

    public void save() throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("settings.json"));
        bos.write(Main.gson.toJson(this).getBytes());
        bos.close();
    }

}
