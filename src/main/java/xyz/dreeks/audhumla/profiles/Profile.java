package xyz.dreeks.audhumla.profiles;

import com.google.gson.annotations.SerializedName;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.exception.ProfileError;
import xyz.dreeks.audhumla.manifests.Version;

import java.io.*;
import java.util.ArrayList;

public class Profile {

    @SerializedName("profile_name")
    public String profileName;

    public transient java.io.File installLocation;

    public transient Version version;

    public Profile() {}

    public Profile(String name) {
        this();
        this.profileName = name;
    }

    public void save() {
        this.installLocation.mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.installLocation, "instance.json")))){
            bw.write(Main.gson.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.profileName;
    }

    public static ArrayList<Profile> load() throws ProfileError {
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
                profile.installLocation = p;

                profiles.add(profile);
            } catch (FileNotFoundException e) {
                errors.add(p.getName());
            }
        }

        if (errors.size() > 0) {
            throw new ProfileError(errors);
        }

        return profiles;
    }

}
