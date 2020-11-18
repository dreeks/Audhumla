package xyz.dreeks.audhumla.downloads;

import java.io.File;

public class Download {

    public String url;
    public File destination;

    public Download(String url, File destination) {
        this.url = url;
        this.destination = destination;
    }

}
