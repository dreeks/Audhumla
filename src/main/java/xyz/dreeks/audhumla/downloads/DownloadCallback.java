package xyz.dreeks.audhumla.downloads;

public interface DownloadCallback {

    void success(Download dl);
    void failedRepeatedly(Download dl);
    void progress(Download dl, double percentage);

}
