package xyz.dreeks.audhumla.manifests;

import xyz.dreeks.audhumla.os.OperatingSystem;
import xyz.dreeks.audhumla.os.Utils;

import java.util.HashMap;

public class Rule {

    public String action;
    public OS os;
    public HashMap<String, Boolean> features;

    public static boolean isAllowed(Rule[] rules) {
        boolean allowed = true;

        for (Rule r : rules) {
            if (r.os == null) {
                allowed = r.action.equalsIgnoreCase("allow");
            } else {
                if (r.os.matches()) {
                    allowed = r.action.equalsIgnoreCase("allow");
                }
            }
        }

        return allowed;
    }
}

class OS {
    public String name;
    public String version;
    public String arch;

    public boolean matches() {
        boolean doesMatch = false;
        if (name.length() > 0) {
            doesMatch = name.equalsIgnoreCase("windows") && Utils.getOS() == OperatingSystem.WINDOWS;
            doesMatch |= name.equalsIgnoreCase("linux") && Utils.getOS() == OperatingSystem.LINUX;
            doesMatch |= name.equalsIgnoreCase("osx") && Utils.getOS() == OperatingSystem.OSX;
        }

        // @TODO: Version

        return doesMatch;
    }
}
