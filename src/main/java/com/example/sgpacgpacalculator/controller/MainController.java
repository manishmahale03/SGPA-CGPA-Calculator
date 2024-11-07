package com.example.sgpacgpacalculator.controller;

import com.example.sgpacgpacalculator.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Label welcomeText;

    public void handleGetStarted(ActionEvent actionEvent) {
        navigateTo("login.fxml", "Login", actionEvent); // Navigate to login page
    }

    private void navigateTo(String fxmlFile, String title, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sgpacgpacalculator/fxml/" + fxmlFile));
            Parent root = loader.load();

            // Use scene dimensions from App.java
            Scene scene = new Scene(root, App.SCENE_WIDTH, App.SCENE_HEIGHT);

            // Set the stage properties
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
