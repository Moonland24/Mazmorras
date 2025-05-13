package com.mazmorras;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal del proyecto que lanza la aplicación JavaFX.
 * Esta clase carga la interfaz gráfica definida en un archivo FXML y configura la escena inicial del juego.
 * @author Inma
 * @author Juanfran
 */
public class Main extends Application {

        /**
     * Método principal de entrada de la aplicación JavaFX.
     * Configura la escena inicial del juego a partir del archivo FXML de creación de personaje.
     *
     * @param primaryStage Escenario principal proporcionado por JavaFX.
     * @throws Exception Si ocurre un error al cargar el archivo FXML o aplicar los estilos.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Carga el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/creacionpersonaje.fxml"));
        Parent root = loader.load();

        //Configura la escena y el stage
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/creacionpersonaje.css").toExternalForm());
        
        //Configuración de la ventana
        primaryStage.setTitle("Dragones y Mazmorras");
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

    /**
     * Método estático que lanza la aplicación JavaFX.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch(args); //Inicia la aplicación JavaFX
    }
}