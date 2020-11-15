package xyz.dreeks.audhumla;

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

}
