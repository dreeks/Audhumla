package xyz.dreeks.audhumla.config;

import com.google.gson.annotations.SerializedName;

public class ConfigDownloads {

    @SerializedName("max_retries")
    public int maxRetries;

    @SerializedName("max_concurrent_downloads")
    public int maxConcurrentDownloads;

}
