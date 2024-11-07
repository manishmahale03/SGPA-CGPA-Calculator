package com.example.sgpacgpacalculator.controller;

import com.example.sgpacgpacalculator.App;
import com.example.sgpacgpacalculator.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            if (validateLogin()) {
                navigateToHome(usernameField.getText());
            } else {
                loginMessageLabel.setText("Invalid Login. Please try again!");
            }
        } else {
            loginMessageLabel.setText("Please enter your username and password!");
        }
    }

    private boolean validateLogin() {
        String query = "SELECT count(1) FROM users WHERE username = ? AND password = ?";
        try (Connection connectDB = new DatabaseConnection().getDatabaseConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {

            preparedStatement.setString(1, usernameField.getText());
            preparedStatement.setString(2, passwordField.getText()); // Ideally, compare hashed passwords

            ResultSet queryResult = preparedStatement.executeQuery();
            return queryResult.next() && queryResult.getInt(1) == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void navigateToHome(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sgpacgpacalculator/fxml/home.fxml"));
            Parent root = loader.load();

            HomeController homeController = loader.getController();
            homeController.setUsername(username);

            Scene scene = new Scene(root, App.SCENE_WIDTH, App.SCENE_HEIGHT);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() {
        navigateTo("register.fxml", "Register");
    }

    private void navigateTo(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sgpacgpacalculator/fxml/" + fxmlFile));
            Parent root = loader.load();

            Scene scene = new Scene(root, App.SCENE_WIDTH, App.SCENE_HEIGHT);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
