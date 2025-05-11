module com.mazmorras {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.mazmorras.controllers to javafx.fxml;
    exports com.mazmorras;
}
