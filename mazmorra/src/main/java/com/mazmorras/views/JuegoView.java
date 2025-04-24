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

}