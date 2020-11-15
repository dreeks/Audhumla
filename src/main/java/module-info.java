module audhumla {
    requires com.google.gson;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;

    opens xyz.dreeks.audhumla to javafx.fxml;
    opens xyz.dreeks.audhumla.gui to javafx.fxml;
    exports xyz.dreeks.audhumla;
}
