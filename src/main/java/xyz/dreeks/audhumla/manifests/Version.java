package xyz.dreeks.audhumla.manifests;

import com.google.gson.annotations.SerializedName;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.profiles.Profile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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



}

class Downloads {
    public File client;
    public File server;
}