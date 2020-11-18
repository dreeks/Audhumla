package xyz.dreeks.audhumla.os;

public class Utils {

    public static OperatingSystem os;

    public static OperatingSystem getOS() {
        if (os != null) {
            return os;
        }

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            Utils.os = OperatingSystem.WINDOWS;
        }

        if (os.contains("nux")) {
            Utils.os = OperatingSystem.LINUX;
        }

        if(os.contains("mac") || os.contains("darwin")) {
            Utils.os = OperatingSystem.OSX;
        }

        return Utils.os;
    }

}
