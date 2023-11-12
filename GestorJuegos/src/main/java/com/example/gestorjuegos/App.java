package com.example.gestorjuegos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene( String fxml,String title ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/"+fxml));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}