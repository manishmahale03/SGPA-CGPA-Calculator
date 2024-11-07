package com.example.sgpacgpacalculator.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultController {

    @FXML
    private Label sgpaLabel;

    @FXML
    private Label cgpaLabel;

    @FXML
    private Label finalResultLabel;

    public void setSGPA(double sgpa) {
        sgpaLabel.setText(String.format("%.2f", sgpa));
    }

    public void setCGPA(double cgpa) {
        cgpaLabel.setText(String.format("%.2f", cgpa));
    }

    public void setFinalResult(String result) {
        finalResultLabel.setText(result);
    }

    @FXML
    private void handleBack() {
        sgpaLabel.getScene().getWindow().hide();
    }
}
