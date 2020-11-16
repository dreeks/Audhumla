module audhumla {
    requires com.google.gson;
    requires javafx.fxml;
    requires javafx.controls;

    opens xyz.dreeks.audhumla to javafx.fxml;
    opens xyz.dreeks.audhumla.gui to javafx.fxml;

    opens xyz.dreeks.audhumla.gui.controls to javafx.fxml;
    opens xyz.dreeks.audhumla.gui.panels to javafx.fxml;

    exports xyz.dreeks.audhumla;
}
