package xyz.dreeks.audhumla.downloads;

import xyz.dreeks.audhumla.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader implements Runnable {

    private Download download;
    private DownloadCallback callbacks;

    public Downloader(Download dl, DownloadCallback callbacks) {
        this.download = dl;
        this.callbacks = callbacks;
    }

    @Override
    public void run() {
        var hasDownloaded = false;
        var amtTries = 0;
        while (!hasDownloaded || amtTries < Main.config.downloads.maxRetries) {
            try {
                URL url = new URL(this.download.url);
                HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
                long filesize = httpConnection.getContentLength();

                BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
                FileOutputStream fos = new FileOutputStream(this.download.destination);
                BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);

                byte[] data = new byte[1024];
                long downloaded = 0;
                int x = 0;

                while ((x = in.read(data, 0, 1024)) >= 0) {
                    downloaded += x;
                    this.callbacks.progress(this.download, (downloaded / filesize) * 100000d);
                    bos.write(data, 0, x);
                }

                bos.close();
                in.close();
                hasDownloaded = true;
                this.callbacks.success(this.download);
            } catch (FileNotFoundException e) {
                System.out.printf("Failed to download (%d/%d): %s\n", amtTries, Main.config.downloads.maxRetries, this.download.url + "\n" + e.getMessage());
            } catch (IOException e) {
                System.out.printf("Failed to download (%d/%d): %s\n", amtTries, Main.config.downloads.maxRetries, this.download.url + "\n" + e.getMessage());
            }

            amtTries++;
        }

        if (amtTries >= Main.config.downloads.maxRetries) {
            this.callbacks.failedRepeatedly(this.download);
        }
    }

}
