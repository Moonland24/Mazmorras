package com.mazmorras.controllers;

// Importación de clases necesarias para la funcionalidad del controlador
import java.io.IOException;

import com.mazmorras.interfaces.PersonajeObserver;
import com.mazmorras.models.Heroe;
import com.mazmorras.models.Personaje;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para la pantalla de creación de personaje.
 * Maneja la lógica de creación y configuración de atributos del héroe.
 * Permite distribuir puntos entre estadísticas y avanzar al mapa con el héroe
 * creado.
 * 
 * @author Inma
 * @author Juanfran
 * @version 1.0
 */
public class CreacionPersonajeController implements PersonajeObserver {

    /** Campos de la interfaz gráfica vinculados con la vista FXML */
    @FXML private TextField nombreField;
    @FXML private Label vidaMaximaLabel;
    @FXML private Label ataqueLabel;
    @FXML private Label defensaLabel;
    @FXML private Label velocidadLabel;
    @FXML private Label errorLabel;
    @FXML private Label puntosRestantesLabel;
    @FXML private Button crearButton;
    @FXML private Button cancelarButton;
    @FXML private Button salirButton;
    @FXML private Button incrementarVidaButton;
    @FXML private Button decrementarVidaButton;
    @FXML private Button incrementarAtaqueButton;
    @FXML private Button decrementarAtaqueButton;
    @FXML private Button incrementarDefensaButton;
    @FXML private Button decrementarDefensaButton;
    @FXML private Button incrementarVelocidadButton;
    @FXML private Button decrementarVelocidadButton;

    /** Atributos del héroe y configuración inicial */
    private int vidaMaxima = 100;
    private int ataque = 10;
    private int defensa = 10;
    private int velocidad = 10;
    private int nivel = 1;
    private int maxPuntos = 20;
    private int puntosRestantes = maxPuntos;
    private Heroe heroeActual;

    /**
     * Implementación del método de la interfaz PersonajeObserver.
     * Este método se llamará cuando el héroe notifique cambios.
     * 
     * @param personaje El personaje que ha sido actualizado
     */
    @Override
    public void onPersonajeActualizado(Personaje personaje) {
        if (personaje instanceof Heroe) {
            heroeActual = (Heroe) personaje;
            // Actualiza solo las etiquetas, no el modelo (para evitar ciclos)
            actualizarUI();
        }
    }

    /**
     * Aumenta la vida máxima si hay puntos restantes.
     */
    @FXML
    private void incrementarVidaMaxima() {
        if (puntosRestantes > 0) {
            vidaMaxima++;
            puntosRestantes--;
            actualizarModelo();
            errorLabel.setText("");
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
    }

    /**
     * Reduce la vida máxima si es mayor a 1 y hay puntos para redistribuir.
     */
    @FXML
    private void decrementarVidaMaxima() {
        if (vidaMaxima > 1 && puntosRestantes < maxPuntos) {
            vidaMaxima--;
            puntosRestantes++;
            actualizarModelo();
            errorLabel.setText("");
        } else if (vidaMaxima <= 1) {
            errorLabel.setText("La vida máxima no puede ser menor a 1.");
        } else {
            errorLabel.setText("No puedes decrementar más atributos.");
        }
    }

    /**
     * Aumenta el ataque si hay puntos restantes.
     */
    @FXML
    private void incrementarAtaque() {
        if (puntosRestantes > 0) {
            ataque++;
            puntosRestantes--;
            actualizarModelo();
            errorLabel.setText("");
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
    }

    /**
     * Reduce el ataque si es mayor a 1 y hay puntos para redistribuir.
     */
    @FXML
    private void decrementarAtaque() {
        if (ataque > 1 && puntosRestantes < maxPuntos) {
            ataque--;
            puntosRestantes++;
            actualizarModelo();
            errorLabel.setText("");
        } else if (ataque <= 1) {
            errorLabel.setText("El ataque no puede ser menor a 1.");
        } else {
            errorLabel.setText("No puedes decrementar más atributos.");
        }
    }

    /**
     * Aumenta la defensa si hay puntos restantes.
     */
    @FXML
    private void incrementarDefensa() {
        if (puntosRestantes > 0) {
            defensa++;
            puntosRestantes--;
            actualizarModelo();
            errorLabel.setText("");
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
    }

    /**
     * Reduce la defensa si es mayor a 1 y hay puntos para redistribuir.
     */
    @FXML
    private void decrementarDefensa() {
        if (defensa > 1 && puntosRestantes < maxPuntos) {
            defensa--;
            puntosRestantes++;
            actualizarModelo();
            errorLabel.setText("");
        } else if (defensa <= 1) {
            errorLabel.setText("La defensa no puede ser menor a 1.");
        } else {
            errorLabel.setText("No puedes decrementar más atributos.");
        }
    }

    /**
     * Aumenta la velocidad si hay puntos restantes.
     */
    @FXML
    private void incrementarVelocidad() {
        if (puntosRestantes > 0) {
            velocidad++;
            puntosRestantes--;
            actualizarModelo();
            errorLabel.setText("");
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
    }

    /**
     * Reduce la velocidad si es mayor a 1 y hay puntos para redistribuir.
     */
    @FXML
    private void decrementarVelocidad() {
        if (velocidad > 1 && puntosRestantes < maxPuntos) {
            velocidad--;
            puntosRestantes++;
            actualizarModelo();
            errorLabel.setText("");
        } else if (velocidad <= 1) {
            errorLabel.setText("La velocidad no puede ser menor a 1.");
        } else {
            errorLabel.setText("No puedes decrementar más atributos.");
        }
    }

    /**
     * Aumenta el nivel del héroe si no supera el límite.
     */
    @FXML
    private void incrementarNivel() {
        if (nivel < 100) {
            nivel++;
            actualizarModelo();
            errorLabel.setText("");
        } else {
            errorLabel.setText("El nivel no puede ser mayor a 100.");
        }
    }

    /**
     * Reduce el nivel del héroe si es mayor a 1.
     */
    @FXML
    private void decrementarNivel() {
        if (nivel > 1) {
            nivel--;
            actualizarModelo();
            errorLabel.setText("");
        } else {
            errorLabel.setText("El nivel no puede ser menor a 1.");
        }
    }

    /**
     * Actualiza solo los elementos de la interfaz de usuario.
     * Este método NO actualiza el modelo.
     */
    private void actualizarUI() {
        vidaMaximaLabel.setText("Vida Máxima: " + vidaMaxima);
        ataqueLabel.setText("Ataque: " + ataque);
        defensaLabel.setText("Defensa: " + defensa);
        velocidadLabel.setText("Velocidad: " + velocidad);
        puntosRestantesLabel.setText("Puntos Restantes: " + puntosRestantes);
    }
    
    /**
     * Actualiza el modelo con los valores actuales.
     * Esta actualización desencadenará una notificación a los observadores
     * que a su vez actualizará la interfaz gráfica.
     */
    private void actualizarModelo() {
        if (heroeActual != null) {
            heroeActual.setVidaMaxima(vidaMaxima);
            heroeActual.setAtaque(ataque);
            heroeActual.setDefensa(defensa);
            heroeActual.setVelocidad(velocidad);
            heroeActual.setNivel(nivel);
            // No es necesario llamar a notifyPersonajeActualizado() aquí
            // ya que los setters del modelo deberían hacerlo internamente
        } else {
            // Solo si aún no existe el modelo, actualizamos directamente la UI
            actualizarUI();
        }
    }

    /**
     * Crea un héroe y carga la siguiente escena del juego.
     * Valida los campos y muestra errores si hay inconsistencias.
     */
    @FXML
    private void crearHeroe() {
        try {
            String nombre = nombreField.getText();

            if (nombre.isEmpty() || vidaMaxima <= 0 || ataque <= 0 || defensa <= 0 || velocidad <= 0 || nivel <= 0) {
                throw new IllegalArgumentException("Todos los campos deben ser válidos y mayores a 0.");
            }

            // Crear el héroe
            heroeActual = new Heroe(nombre, 0, 0, vidaMaxima, ataque, defensa, velocidad, nivel);

            // Suscribir este controlador como observador del héroe
            heroeActual.addObserver(this);

            if (heroeActual != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mapaTest.fxml"));
                try {
                    Parent root = loader.load();
                    JuegoController juegoController = loader.getController();
                    juegoController.recibirHeroe(heroeActual);

                    Stage stage = (Stage) nombreField.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/creacionpersonaje.css").toExternalForm());

                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();

                } catch (IOException e) {
                    errorLabel.setText("Error al cargar la siguiente escena: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    /**
     * Restablece los valores iniciales de los atributos y limpia el campo de
     * nombre.
     */
    @FXML
    private void cancelarCreacion() {
        nombreField.clear();
        vidaMaxima = 100;
        ataque = 10;
        defensa = 10;
        velocidad = 10;
        nivel = 1;
        puntosRestantes = maxPuntos;
        actualizarModelo();
        errorLabel.setText("");
    }

    /**
     * Cierra la aplicación completamente.
     */
    @FXML
    private void salirAplicacion() {
        System.exit(0);
    }

    /**
     * Inicializa las etiquetas y los eventos de los botones al cargar la vista.
     */
    @FXML
    public void initialize() {
        // Configurar los manejadores de eventos para los botones de atributos
        incrementarVidaButton.setOnAction(event -> incrementarVidaMaxima());
        decrementarVidaButton.setOnAction(event -> decrementarVidaMaxima());
        incrementarAtaqueButton.setOnAction(event -> incrementarAtaque());
        decrementarAtaqueButton.setOnAction(event -> decrementarAtaque());
        incrementarDefensaButton.setOnAction(event -> incrementarDefensa());
        decrementarDefensaButton.setOnAction(event -> decrementarDefensa());
        incrementarVelocidadButton.setOnAction(event -> incrementarVelocidad());
        decrementarVelocidadButton.setOnAction(event -> decrementarVelocidad());

        // Configurar los manejadores de eventos para los botones principales
        crearButton.setOnAction(event -> {
            try {
                crearHeroe();
            } catch (Exception e) {
                errorLabel.setText("Error al crear el héroe: " + e.getMessage());
            }
        });

        cancelarButton.setOnAction(event -> cancelarCreacion());
        salirButton.setOnAction(event -> salirAplicacion());

        // Crear un héroe temporal para ir reflejando los cambios
        heroeActual = new Heroe("Temporal", 0, 0, vidaMaxima, ataque, defensa, velocidad, nivel);
        heroeActual.addObserver(this);

        // Inicializar valores por defecto
        actualizarUI();
        errorLabel.setText("");
    }
}