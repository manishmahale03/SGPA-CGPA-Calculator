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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SGPAController {
    @FXML
    private GridPane subjectsGrid; // Reference to the GridPane for subject entries
    @FXML
    private Label resultLabel; // Reference to the result label

    private int subjectCount = 0; // Track the number of subjects added
    private ArrayList<Double> sgpaList = new ArrayList<>(); // Store SGPA values for CGPA
    private ArrayList<Double> creditList = new ArrayList<>(); // Store credits for CGPA
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void initialize() {
        // Initialize the first subject row
        addSubjectRow();
    }

    @FXML
    public void calculateSGPA(ActionEvent actionEvent) {
        double totalGradePoints = 0.0;
        double totalCredits = 0.0;

        // Iterate through each subject row in the GridPane
        for (int i = 1; i <= subjectCount; i++) { // Start from row 1, as row 0 contains headers
            TextField gradeField = (TextField) subjectsGrid.getChildren().get(i * 3 + 1); // Grade field
            TextField creditField = (TextField) subjectsGrid.getChildren().get(i * 3 + 2); // Credit field

            String gradeText = gradeField.getText();
            String creditText = creditField.getText();

            // Check if fields are empty
            if (gradeText.isEmpty() || creditText.isEmpty()) {
                resultLabel.setText("Please fill in all grade and credit fields.");
                return;
            }

            try {
                double grade = Double.parseDouble(gradeText);
                double credits = Double.parseDouble(creditText);

                // Check for valid grade and credit values
                if (grade < 0 || credits <= 0) {
                    resultLabel.setText("Grade must be non-negative and credits must be positive.");
                    return;
                }

                // Accumulate total grade points and credits
                totalGradePoints += grade * credits;
                totalCredits += credits;
            } catch (NumberFormatException e) {
                resultLabel.setText("Please enter valid numeric values for grade and credit.");
                return;
            }
        }

        // Calculate and display SGPA
        double sgpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
        resultLabel.setText(String.format("SGPA: %.2f", sgpa));

        // Store SGPA and credits for CGPA calculation
        addSGPA(sgpa, totalCredits);
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        navigateTo("home.fxml", "Home", actionEvent, username);
    }

    private void navigateTo(String fxmlFile, String title, ActionEvent actionEvent, String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sgpacgpacalculator/fxml/" + fxmlFile));
            Parent root = loader.load();

            if (fxmlFile.equals("home.fxml")) {
                HomeController homeController = loader.getController();
                homeController.setUsername(username);
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

    public void handleRemoveSubject(ActionEvent actionEvent) {
        if (subjectCount > 1) { // Ensure at least one subject remains
            int lastRowIndex = subjectCount;
            subjectsGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == lastRowIndex); // Remove last row
            subjectCount--;
        }
    }

    public void handleAddSubject(ActionEvent actionEvent) {
        addSubjectRow();
    }

    private void addSubjectRow() {
        TextField subjectField = new TextField();
        subjectField.setPromptText("Enter Subject Name");

        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter Grade");

        TextField creditField = new TextField();
        creditField.setPromptText("Enter Credit Points");

        subjectsGrid.add(subjectField, 0, subjectCount + 1); // Column 0
        subjectsGrid.add(gradeField, 1, subjectCount + 1); // Column 1
        subjectsGrid.add(creditField, 2, subjectCount + 1); // Column 2

        subjectCount++;
    }

    public void addSGPA(double sgpa, double credits) {
        sgpaList.add(sgpa);
        creditList.add(credits);
        // Optional: add CGPA calculation
    }
}
