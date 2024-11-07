package com.example.sgpacgpacalculator.controller;

import com.example.sgpacgpacalculator.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Label welcomeText;
    private String username;

    // Initializes the welcome text once the FXML is loaded
    @FXML
    public void initialize() {
        if (username != null) {
            welcomeText.setText("Welcome, " + username + "!");
        }
    }

    public void setUsername(String username) {
        this.username = username;
        if (welcomeText != null) {
            welcomeText.setText("Welcome, " + username + "!");
        }
    }

    @FXML
    public void handleCGPA(ActionEvent actionEvent) {
        navigateTo("cgpa.fxml", "CGPA Calculator", actionEvent);
    }

    @FXML
    public void handleSGPA(ActionEvent actionEvent) {
        navigateTo("sgpa.fxml", "SGPA Calculator", actionEvent);
    }

    @FXML
    public void handleProfile(ActionEvent actionEvent) {
        navigateTo("profile.fxml", "Profile", actionEvent);
    }

    @FXML
    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    private void navigateTo(String fxmlFile, String title, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sgpacgpacalculator/fxml/" + fxmlFile));
            Parent root = loader.load();

            // Set the username in the controller if it has a setUsername method
            Object controller = loader.getController();
            if (controller instanceof CGPAController) {
                ((CGPAController) controller).setUsername(username);
            } else if (controller instanceof SGPAController) {
                ((SGPAController) controller).setUsername(username);
            }
            else if (controller instanceof ProfileController) {
                ((ProfileController) controller).setUsername(username);
            }

            Scene scene = new Scene(root, App.SCENE_WIDTH, App.SCENE_HEIGHT);
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
