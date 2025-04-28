package com.mazmorras.views;

import com.mazmorras.models.Heroe;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CreacionPersonajeView {
    private VBox container;
    private Label errorLabel;

    public CreacionPersonajeView() {
        container = new VBox(10);
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        container.getChildren().add(errorLabel);
    }

    public void mostrarDetallesHeroe(Heroe heroe) {
        container.getChildren().add(new Label("Héroe creado: " + heroe.getNombre()));
        container.getChildren().add(new Label("Stats: ATK=" + heroe.getAtaque() + ", DEF=" + heroe.getDefensa()));
    }

    public void mostrarError(String mensaje) {
        errorLabel.setText(mensaje);
    }

    public VBox getView() {
        return container;
    }
}