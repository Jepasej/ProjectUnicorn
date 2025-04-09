module org.example.frankjavafxhomework {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.frankjavafxhomework to javafx.fxml;
    exports org.example.frankjavafxhomework;
}