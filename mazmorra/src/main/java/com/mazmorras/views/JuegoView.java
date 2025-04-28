package com.mazmorras.views;

import com.mazmorras.controllers.JuegoController;
import com.mazmorras.interfaces.JuegoObserver;
import com.mazmorras.models.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class JuegoView implements JuegoObserver {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label turnoIndicador;
    @FXML
    private Label statsLabel;

    private JuegoController controller;
    private static final int TILE_SIZE = 40; // Tamaño de cada celda en píxeles
    public void actualizarTablero(Mapa mapa, Heroe heroe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarTablero'");
    }
    public void actualizarStats(Heroe heroe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarStats'");
    }
    public void actualizarTurno(Object turnoActual) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarTurno'");
    }
    public void mostrarGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarGameOver'");
    }

}