package xyz.dreeks.audhumla.manifests;

import java.util.HashMap;

public class Library {
    public String name;
    public File artifact;
    public HashMap<String, File> natives;
    // @TODO: extract on mojang's official libs (Ex: 1.16.3 / "ca.weblite:java-objc-bridge:1.0.0")
    public Rule[] rules;

    public String getPath() {
        return this.artifact.path.substring(0, this.artifact.path.lastIndexOf("/"));
    }

}
