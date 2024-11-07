package com.example.sgpacgpacalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static final double SCENE_WIDTH = 500;  // Set new width here
    public static final double SCENE_HEIGHT = 900; // Set new height here


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);

        // Set the fixed window title
        stage.setTitle("SGPA/CGPA Calculator");

        // Set the fixed window size
        stage.setScene(scene);
        stage.setResizable(false); // Disable resizing

        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}
