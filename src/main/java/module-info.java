module com.example.sgpacgpacalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jdk.compiler;


    opens com.example.sgpacgpacalculator to javafx.fxml;

    exports com.example.sgpacgpacalculator;
    exports com.example.sgpacgpacalculator.controller;

    opens com.example.sgpacgpacalculator.controller to javafx.fxml;
}