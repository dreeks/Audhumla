package xyz.dreeks.audhumla.downloads;

import xyz.dreeks.audhumla.Main;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadQueue {

    private ArrayList<Download> downloads;
    private ExecutorService es;

    public DownloadQueue() {
        this.downloads = new ArrayList<>();
        this.es = Executors.newFixedThreadPool(Main.config.downloads.maxConcurrentDownloads);
    }

    public void start(DownloadCallback callback) {
        for (Download dl : this.downloads) {
            Downloader downloader = new Downloader(dl, callback);
            this.es.execute(downloader);
        }
    }

    public void addFile(Download download) {
        synchronized (this.downloads) {
            this.downloads.add(download);
        }
    }

}
