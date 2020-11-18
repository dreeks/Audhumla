package xyz.dreeks.audhumla.utils;

import javafx.scene.image.Image;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.manifests.ManifestVersion;
import xyz.dreeks.audhumla.manifests.Version;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {

    public static String fetchURL(String url) throws Exception {
        BufferedReader br = null;
        try {
            URL u = new URL(url);
            br = new BufferedReader(new InputStreamReader(u.openStream()));
            StringBuffer buf = new StringBuffer();

            int read;
            char[] chars = new char[1024];
            while((read = br.read(chars)) != -1) {
                buf.append(chars, 0, read);
            }

            return buf.toString();
        } finally {
            if (br != null)
                br.close();
        }
    }

    public static Version fetchVersion(ManifestVersion mv) throws Exception {
        return Main.gson.fromJson(fetchURL(mv.url), Version.class);
    }

    public static boolean isValidDirectoryName(String s) {
        if (s.equalsIgnoreCase("con") || s.equalsIgnoreCase("prn") || s.equalsIgnoreCase("aux") || s.equalsIgnoreCase("nul") || s.equalsIgnoreCase("com"))
            return false;

        return s.matches("^[a-zA-Z0-9.\\-_ \\[\\]\\(\\)]+$");
    }

    public static Image loadImageForUser(String uuid) {
        if (uuid == null) {
            return new Image(Utils.class.getResource("/xyz/dreeks/audhumla/gui/default_head.png").toExternalForm());
        }
        Image i = new Image(Main.config.getCrafatarURL(uuid));
        if (i.isError()) {
            i = new Image(Utils.class.getResource("/xyz/dreeks/audhumla/gui/default_head.png").toExternalForm());
        }

        return i;
    }
}
