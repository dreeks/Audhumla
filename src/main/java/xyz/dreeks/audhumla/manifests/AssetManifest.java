package xyz.dreeks.audhumla.manifests;

import com.google.gson.annotations.SerializedName;

public class AssetManifest {

    public String id;
    public String url;
    public long size;
    public long totalSize;

    @SerializedName("sha1")
    public String hash;


}
