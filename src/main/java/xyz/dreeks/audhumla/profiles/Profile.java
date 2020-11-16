package xyz.dreeks.audhumla.profiles;

import com.google.gson.annotations.SerializedName;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.exception.ProfileError;
import xyz.dreeks.audhumla.manifests.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Profile {

    @SerializedName("profile_name")
    public String profileName;

    public transient Version version;

    public Profile() {}

    public Profile(String name) {
        this();
        this.profileName = name;
    }

    public static ObservableList<Profile> load() throws ProfileError {
        ArrayList<Profile> profiles = new ArrayList<>();

        File f = new File("instances");
        f.mkdirs();

        ArrayList<String> errors = new ArrayList<>();

        for (File p : f.listFiles()) {
            if (!p.isDirectory())
                continue;

            try {
                Profile profile = Main.gson.fromJson(new BufferedReader(new FileReader(new File(p, "instance.json"))), Profile.class);
                profile.version = Main.gson.fromJson(new BufferedReader(new FileReader(new File(p, "version.json"))), Version.class);
                profiles.add(profile);
            } catch (FileNotFoundException e) {
                errors.add(p.getName());
            }
        }

        if (errors.size() > 0) {
            throw new ProfileError(errors);
        }

        return FXCollections.observableArrayList(profiles);
    }

}
