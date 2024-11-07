package com.example.sgpacgpacalculator.controller;

import com.example.sgpacgpacalculator.App;
import com.example.sgpacgpacalculator.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {

    @FXML
    private ImageView profileIcon;

    @FXML
    private Label welcomeText;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    private String username;

    private boolean dataLoaded = false;  // Flag to prevent multiple data loads

    @FXML
    public void initialize() {
        // Load the profile image only once when the controller is initialized
        Image image = new Image(getClass().getResourceAsStream("/com/example/sgpacgpacalculator/fxml/images/Profile.png"));
        profileIcon.setImage(image);
    }

    public void setUsername(String username) {
        this.username = username;
        loadUserData();
    }

    private void loadUserData() {
        if (dataLoaded) return;

        DatabaseConnection connectNow = new DatabaseConnection();
        try (Connection connectDB = connectNow.getDatabaseConnection()) {
            String query = "SELECT firstName, lastName, username, email FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    firstNameLabel.setText(resultSet.getString("firstName"));
                    lastNameLabel.setText(resultSet.getString("lastName"));
                    usernameLabel.setText(resultSet.getString("username"));
                    emailLabel.setText(resultSet.getString("email"));
                    welcomeText.setText("Welcome, " + resultSet.getString("firstName") + "!");
                    dataLoaded = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        navigateTo("home.fxml", "Home", actionEvent);
    }

    @FXML
    public void handleLogout(ActionEvent actionEvent) {
        navigateTo("login.fxml", "Login", actionEvent);
    }

    private void navigateTo(String fxmlFile, String title, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sgpacgpacalculator/fxml/" + fxmlFile));
            Parent root = loader.load();

            // Pass the username to the HomeController if navigating to home
            if (fxmlFile.equals("home.fxml")) {
                HomeController homeController = loader.getController();
                homeController.setUsername(username);
            }

            Scene scene = new Scene(root, App.SCENE_WIDTH, App.SCENE_HEIGHT);
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
