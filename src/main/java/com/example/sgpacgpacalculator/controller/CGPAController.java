package com.example.sgpacgpacalculator.controller;

import com.example.sgpacgpacalculator.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CGPAController {

    @FXML
    private VBox mainVBox;

    @FXML
    private GridPane semesterGrid;

    @FXML
    private Label resultLabel;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    private int semesterCount = 1;
    private ArrayList<TextField> sgpaFields = new ArrayList<>();

    @FXML
    public void initialize() {
        resetFields();
    }

    public void resetFields() {
        // Clear all semester data
        semesterCount = 1;
        sgpaFields.clear();
        semesterGrid.getChildren().clear(); // Clear all nodes in the grid

        // Initialize the first semester's input fields
        Label semesterLabel = new Label("Semester 1");
        semesterLabel.getStyleClass().add("input-label");

        TextField sgpaField = new TextField();
        sgpaField.setPromptText("SGPA");
        sgpaField.setId("sgpaField1");

        semesterGrid.add(semesterLabel, 0, 1);
        semesterGrid.add(sgpaField, 1, 1);

        sgpaFields.add(sgpaField);

        resultLabel.setText("");
    }

    @FXML
    private void handleAddSemester(ActionEvent event) {
        semesterCount++;

        Label semesterLabel = new Label("Semester " + semesterCount);
        semesterLabel.getStyleClass().add("input-label");

        TextField sgpaField = new TextField();
        sgpaField.setPromptText("SGPA");

        // Add new semester fields to the GridPane
        semesterGrid.add(semesterLabel, 0, semesterCount);
        semesterGrid.add(sgpaField, 1, semesterCount);

        sgpaFields.add(sgpaField);
    }

    @FXML
    private void handleRemoveSemester(ActionEvent event) {
        if (semesterCount > 1) {
            // Remove last added semester entries
            semesterGrid.getChildren().remove(semesterGrid.getChildren().size() - 1); // SGPA field
            semesterGrid.getChildren().remove(semesterGrid.getChildren().size() - 1); // Semester label

            sgpaFields.remove(sgpaFields.size() - 1);
            semesterCount--;
        }
    }

    @FXML
    private void handleCalculateCGPA(ActionEvent event) {
        double totalSGPA = 0.0;

        for (int i = 0; i < semesterCount; i++) {
            try {
                double sgpa = Double.parseDouble(sgpaFields.get(i).getText());
                totalSGPA += sgpa;

            } catch (NumberFormatException e) {
                resultLabel.setText("Please enter valid numbers for SGPA.");
                return;
            }
        }

        double cgpa = totalSGPA / semesterCount;
        resultLabel.setText(String.format("Your CGPA is: %.2f", cgpa));
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        navigateTo("home.fxml", "Home", actionEvent, username);  // Pass username back to Home
    }

    private void navigateTo(String fxmlFile, String title, ActionEvent actionEvent, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sgpacgpacalculator/fxml/" + fxmlFile));
            Parent root = loader.load();

            // Set the username in HomeController
            if (fxmlFile.equals("home.fxml")) {
                HomeController homeController = loader.getController();
                homeController.setUsername(username);  // Pass the username back
            }

            Scene scene = new Scene(root, App.SCENE_WIDTH, App.SCENE_HEIGHT);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
