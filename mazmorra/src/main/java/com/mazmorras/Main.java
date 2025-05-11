package com.mazmorras;

import com.mazmorras.controllers.JuegoController;
import com.mazmorras.models.Juego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/creacionpersonaje.fxml"));
        Parent root = loader.load();

        // Configurar la escena y el stage
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/creacionpersonaje.css").toExternalForm());
        
        // Configuración de la ventana
        primaryStage.setTitle("Dragones y Mazmorras");
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Inicia la aplicación JavaFX
    }
}