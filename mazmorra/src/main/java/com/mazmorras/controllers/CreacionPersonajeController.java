package com.mazmorras.controllers;

import com.mazmorras.models.Heroe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private Label nivelLabel;
    @FXML
    private Label errorLabel;

    private int vidaMaxima = 100;
    private int ataque = 10;
    private int defensa = 10;
    private int velocidad = 10;
    private int nivel = 1;

    @FXML
    private void incrementarVidaMaxima() {
        vidaMaxima++;
        actualizarLabels();
    }

    @FXML
    private void decrementarVidaMaxima() {
        if (vidaMaxima > 1) {
            vidaMaxima--;
            actualizarLabels();
        }
    }

    @FXML
    private void incrementarAtaque() {
        ataque++;
        actualizarLabels();
    }

    @FXML
    private void decrementarAtaque() {
        if (ataque > 1) {
            ataque--;
            actualizarLabels();
        }
    }

    @FXML
    private void incrementarDefensa() {
        defensa++;
        actualizarLabels();
    }

    @FXML
    private void decrementarDefensa() {
        if (defensa > 1) {
            defensa--;
            actualizarLabels();
        }
    }

    @FXML
    private void incrementarVelocidad() {
        velocidad++;
        actualizarLabels();
    }

    @FXML
    private void decrementarVelocidad() {
        if (velocidad > 1) {
            velocidad--;
            actualizarLabels();
        }
    }

    @FXML
    private void incrementarNivel() {
        nivel++;
        actualizarLabels();
    }

    @FXML
    private void decrementarNivel() {
        if (nivel > 1) {
            nivel--;
            actualizarLabels();
        }
    }

    private void actualizarLabels() {
        vidaMaximaLabel.setText("Vida Máxima: " + vidaMaxima);
        ataqueLabel.setText("Ataque: " + ataque);
        defensaLabel.setText("Defensa: " + defensa);
        velocidadLabel.setText("Velocidad: " + velocidad);
        nivelLabel.setText("Nivel: " + nivel);
    }

    @FXML
    private void crearHeroe() {
        try {
            String nombre = nombreField.getText();

            if (nombre.isEmpty() || vidaMaxima <= 0 || ataque <= 0 || defensa <= 0 || velocidad <= 0 || nivel <= 0) {
                throw new IllegalArgumentException("Todos los campos deben ser válidos y mayores a 0.");
            }

            Heroe heroe = new Heroe(nombre, 0, 0, vidaMaxima, ataque, defensa, velocidad, nivel);
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
}
