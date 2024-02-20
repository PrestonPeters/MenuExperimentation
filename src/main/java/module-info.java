module com.example.project481 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project481 to javafx.fxml;
    exports com.example.project481;
}