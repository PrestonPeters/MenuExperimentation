package com.example.project481;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new MainUI(), 800, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}