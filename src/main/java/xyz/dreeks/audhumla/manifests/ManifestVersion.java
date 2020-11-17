package xyz.dreeks.audhumla.manifests;

public class ManifestVersion {
    public String id;
    public String name;
    public String url;
    public String type;

    @Override
    public String toString() {
        return name;
    }
}
