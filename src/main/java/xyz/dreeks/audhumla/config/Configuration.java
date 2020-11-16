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

    @SerializedName("libraries_url")
    public String librariesURL;

    @SerializedName("yggdrasil_url")
    public String yggdrasilURL;

    @SerializedName("crafatar_url")
    public String crafatarURL;

    public static Configuration load() {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new InputStreamReader(Configuration.class.getResourceAsStream("/config.json")));

        Configuration cfg = gson.fromJson(br, Configuration.class);

        if (!cfg.assetsURL.endsWith("/")) {
            cfg.assetsURL = cfg.assetsURL + "/";
        }

        if (!cfg.librariesURL.endsWith("/")) {
            cfg.librariesURL = cfg.librariesURL + "/";
        }

        if (!cfg.crafatarURL.endsWith("/")) {
            cfg.crafatarURL = cfg.crafatarURL + "/";
        }

        return cfg;
    }

    public String getCrafatarURL(String id) {
        return this.crafatarURL + (this.crafatarURL.endsWith("/") ? "" : "/") + "avatars/" + id;
    }

}
