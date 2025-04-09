module com.example.tp_poo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.tp_poo2 to javafx.fxml;
    exports com.example.tp_poo2;
}