module com.mazmorras {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mazmorras to javafx.fxml;
    exports com.mazmorras;
}
