package xyz.dreeks.audhumla.config;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Configuration {

    @SerializedName("launcher_title")
    public String launcherTitle;

    @SerializedName("version_manifest")
    public String versionManifest;

    @SerializedName("assets_url")
    public String assetsURL;

    @SerializedName("crafatar_url")
    public String crafatarURL;

    public static Configuration load() {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new InputStreamReader(Configuration.class.getResourceAsStream("/config.json")));

        return gson.fromJson(br, Configuration.class);
    }

}
