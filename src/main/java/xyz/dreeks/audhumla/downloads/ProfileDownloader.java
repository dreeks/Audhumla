package xyz.dreeks.audhumla.downloads;

import xyz.dreeks.audhumla.manifests.Library;
import xyz.dreeks.audhumla.manifests.Rule;
import xyz.dreeks.audhumla.profiles.Profile;
import xyz.dreeks.audhumla.utils.Hashing;

import java.io.File;

public class ProfileDownloader {

    private DownloadQueue downloadQueue;
    private Profile profile;

    public ProfileDownloader(Profile p) {
        this.profile = p;
        this.downloadQueue = new DownloadQueue();
    }

    private void downloadAssets() {

    }

    private void downloadLibraries() {
        for (Library lib : this.profile.version.libraries) {
            if (Rule.isAllowed(lib.rules)) {
                // @TODO Check hash + download + same for natives
            }
        }
    }

    private void downloadJars() throws Exception {
        File jar = new File(this.profile.installLocation, "client.jar");
        jar.getParentFile().mkdirs();

        if (!jar.exists() || profile.version.jars.client.hash.equalsIgnoreCase(Hashing.hash(jar))) {
            Download dl = new Download(profile.version.jars.client.url, jar);
            this.downloadQueue.addFile(dl);
        }
    }

    public void downloadAll() {
        try {
            this.downloadJars();
            this.downloadAssets();
            this.downloadLibraries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
