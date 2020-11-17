package xyz.dreeks.audhumla.manifests;

import com.google.gson.annotations.SerializedName;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.profiles.Profile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Version {

    public String id;
    public String name;
    public String type;
    public Date release;

    @SerializedName("main_class")
    public String mainClass;

    public Downloads jars;

    public AssetManifest assets;
    public Library[] libraries;

    @SerializedName("jvm_args")
    public Argument[] jvmArgs;

    @SerializedName("game_args")
    public Argument[] gameArgs;

    public void save(Profile p) {
        java.io.File to = new java.io.File(p.installLocation, "version.json");
        to.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(to))){
            bw.write(Main.gson.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class Downloads {
    public File client;
    public File server;
}