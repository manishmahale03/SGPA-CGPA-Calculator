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

public class RegisterController {

    @FXML
    private Label registerMessageLabel;
    @FXML
    private TextField firstNameField, lastNameField, usernameField, emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleRegister() {
        if (isFormFilled()) {
            if (isUserUnique()) {
                registerUser();
            } else {
                registerMessageLabel.setText("Username or email already exists.");
            }
        } else {
            registerMessageLabel.setText("Please fill all the fields");
        }
    }

    private boolean isFormFilled() {
        return !firstNameField.getText().isBlank() &&
                !lastNameField.getText().isBlank() &&
                !usernameField.getText().isBlank() &&
                !emailField.getText().isBlank() &&
                !passwordField.getText().isBlank();
    }

    private boolean isUserUnique() {
        String query = "SELECT count(1) FROM users WHERE username = ? OR email = ?";
        try (Connection connectDB = new DatabaseConnection().getDatabaseConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {

            preparedStatement.setString(1, usernameField.getText());
            preparedStatement.setString(2, emailField.getText());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) == 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void registerUser() {
        String query = "INSERT INTO users (firstName, lastName, username, email, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection connectDB = new DatabaseConnection().getDatabaseConnection();
             PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {

            preparedStatement.setString(1, firstNameField.getText());
            preparedStatement.setString(2, lastNameField.getText());
            preparedStatement.setString(3, usernameField.getText());
            preparedStatement.setString(4, emailField.getText());
            preparedStatement.setString(5, passwordField.getText()); // Ideally, hash this password

            int rowsAffected = preparedStatement.executeUpdate();
            registerMessageLabel.setText(rowsAffected > 0 ? "User registered successfully." : "Registration failed.");

        } catch (SQLException e) {
            e.printStackTrace();
            registerMessageLabel.setText("An error occurred. Please try again.");
        }
    }

    @FXML
    private void goToLogin() {
        navigateTo("login.fxml", "Login");
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
