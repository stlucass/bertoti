package com.example.ollama.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat-view.fxml"));
        Parent root = loader.load();
        stage.setTitle("Ollama Qwen3 Chat (JavaFX)");
        stage.setScene(new Scene(root, 800, 560));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
