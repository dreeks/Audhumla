package xyz.dreeks.audhumla.os;

import javafx.stage.Stage;
import xyz.dreeks.audhumla.jni.JNIVibrancyWindows;

public class VibrancyWindows implements Vibrancy {

    static {
        System.load("C:\\Users\\Oxodao\\Git\\audhumla\\bindings\\vibrancy_windows\\vibrancy_windows.dll");
    }

    @Override
    public void applyVibrancy(Stage s) {
        JNIVibrancyWindows jni = new JNIVibrancyWindows();
        int foundWindow = jni.SetWindowAcrylic(s.getTitle());
        if (foundWindow != 0) {
            System.out.println("Couldn't find Window to apply vibrancy :(");
        }
    }

}
