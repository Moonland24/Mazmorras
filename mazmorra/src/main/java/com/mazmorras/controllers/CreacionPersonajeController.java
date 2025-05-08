package com.mazmorras.controllers;

import java.io.IOException;

import com.mazmorras.models.Heroe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreacionPersonajeController {

    @FXML
    private TextField nombreField;
    @FXML
    private Label vidaMaximaLabel;
    @FXML
    private Label ataqueLabel;
    @FXML
    private Label defensaLabel;
    @FXML
    private Label velocidadLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label puntosRestantesLabel;

    private int vidaMaxima = 100;
    private int ataque = 10;
    private int defensa = 10;
    private int velocidad = 10;
    private int nivel = 1;
    private int maxPuntos = 20; // Puntos máximos para distribuir entre atributos

    private int puntosRestantes = maxPuntos; // Puntos restantes para distribuir

    @FXML
    private void incrementarVidaMaxima() {
        if (puntosRestantes > 0) {
            vidaMaxima++;
            puntosRestantes--;
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
        actualizarLabels();
    }

    @FXML
    private void decrementarVidaMaxima() {
        if (vidaMaxima > 1 && puntosRestantes < maxPuntos) {
            vidaMaxima--;
            puntosRestantes++;
        } else if (vidaMaxima <= 1) {
            errorLabel.setText("La vida máxima no puede ser menor a 1.");
        } else {
            errorLabel.setText("No puedes decrementar más atributos.");
        }
        actualizarLabels();
    }

    @FXML
    private void incrementarAtaque() {
        if (puntosRestantes > 0) {
            ataque++;
            puntosRestantes--;
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
        actualizarLabels();
    }

    @FXML
    private void decrementarAtaque() {
        if (ataque > 1 && puntosRestantes < maxPuntos) {
            ataque--;
            puntosRestantes++;
        } else if (ataque <= 1) {
            errorLabel.setText("El ataque no puede ser menor a 1.");
        } else {
            errorLabel.setText("No puedes decrementar más atributos.");
        }
        actualizarLabels();
    }

    @FXML
    private void incrementarDefensa() {
        if (puntosRestantes > 0) {
            defensa++;
            puntosRestantes--;
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
        actualizarLabels();
    }

    @FXML
    private void decrementarDefensa() {
        if (defensa > 1 && puntosRestantes < maxPuntos) {
            defensa--;
            puntosRestantes++;
        } else if (defensa <= 1) {
            errorLabel.setText("La defensa no puede ser menor a 1.");
        } else {
            errorLabel.setText("No puedes decrementar más atributos.");
        }
        actualizarLabels();
    }

    @FXML
    private void incrementarVelocidad() {
        if (puntosRestantes > 0) {
            velocidad++;
            puntosRestantes--;
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
        actualizarLabels();
    }

    @FXML
    private void decrementarVelocidad() {
        if (velocidad > 1 && puntosRestantes < maxPuntos) {
            velocidad--;
            puntosRestantes++;
        } else if (velocidad <= 1) {
            errorLabel.setText("La velocidad no puede ser menor a 1.");
        } else {
            errorLabel.setText("No puedes decrementar más atributos.");
        }
        actualizarLabels();
    }

    @FXML
    private void incrementarNivel() {
        if (nivel < 100) { // Suponiendo que el nivel máximo es 100
            nivel++;
            actualizarLabels();
        } else {
            errorLabel.setText("El nivel no puede ser mayor a 100.");
        }
        actualizarLabels();
    }

    @FXML
    private void decrementarNivel() {
        if (nivel > 1) {
            nivel--;
            actualizarLabels();
        } else {
            errorLabel.setText("El nivel no puede ser menor a 1.");
        }
        actualizarLabels();
    }

    private void actualizarLabels() {
        vidaMaximaLabel.setText("Vida Máxima: " + vidaMaxima);
        ataqueLabel.setText("Ataque: " + ataque);
        defensaLabel.setText("Defensa: " + defensa);
        velocidadLabel.setText("Velocidad: " + velocidad);
        puntosRestantesLabel.setText("Puntos Restantes: " + puntosRestantes);
        errorLabel.setText(""); // Limpiar el mensaje de error al actualizar
    }

    @FXML
    private void crearHeroe() {
        try {
            String nombre = nombreField.getText();

            if (nombre.isEmpty() || vidaMaxima <= 0 || ataque <= 0 || defensa <= 0 || velocidad <= 0 || nivel <= 0) {
                throw new IllegalArgumentException("Todos los campos deben ser válidos y mayores a 0.");
            }

            Heroe heroe = new Heroe(nombre, 0, 0, vidaMaxima, ataque, defensa, velocidad, nivel);
            if (heroe != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mapaTest.fxml"));
                try {
                    Parent root = loader.load();
                    JuegoController juegoController = loader.getController();
                    juegoController.recibirHeroe(heroe);
                    
                    // Obtener la ventana y sus dimensiones actuales
                    Stage stage = (Stage) nombreField.getScene().getWindow();
                    double width = stage.getWidth();
                    double height = stage.getHeight();
                    boolean isMaximized = stage.isMaximized();
                    
                    // Cambiar la escena manteniendo las dimensiones
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    
                    if (isMaximized) {
                        stage.setMaximized(true);
                    } else {
                        stage.setWidth(width);
                        stage.setHeight(height);
                    }
                    
                    stage.show();
                } catch (IOException e) {
                    errorLabel.setText("Error al cargar la siguiente escena: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            errorLabel.setText("Héroe creado: " + heroe.toString());
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void cancelarCreacion() {
        nombreField.clear();
        vidaMaxima = 100;
        ataque = 10;
        defensa = 10;
        velocidad = 10;
        nivel = 1;
        actualizarLabels();
        errorLabel.setText("");
    }

    @FXML
    private void salirAplicacion() {
        System.exit(0);
    }

    @FXML
    private void initialize() {
        // Inicializar los labels al cargar la vista
        actualizarLabels();
        errorLabel.setText(""); // Limpiar el mensaje de error al iniciar
    }
}
