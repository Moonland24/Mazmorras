package com.mazmorras.controllers;

// Importación de clases necesarias para la funcionalidad del controlador
import java.io.IOException;
import com.mazmorras.models.Heroe;       
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
 * Permite distribuir puntos entre estadísticas y avanzar al mapa con el héroe creado.
 * 
 * @author Inma
 * @author Juanfran
 * @version 1.0
 */
public class CreacionPersonajeController {
    

    /**Campos de la interfaz gráfica vinculados con la vista FXML */
    /**Campo para ingresar el nombre del héroe*/
    @FXML
    private TextField nombreField;
    /**Etiqueta para mostrar la vida máxima*/      
    @FXML
    private Label vidaMaximaLabel; 
    /**Etiqueta para mostrar el ataque*/     
    @FXML
    private Label ataqueLabel;
    /**Etiqueta para mostrar la defensa*/          
    @FXML
    private Label defensaLabel; 
    /**Etiqueta para mostrar la velocidad*/        
    @FXML
    private Label velocidadLabel; 
    /**Etiqueta para mostrar mensajes de error*/      
    @FXML
    private Label errorLabel; 
    /**Etiqueta para mostrar los puntos restantes*/          
    @FXML
    private Label puntosRestantesLabel;
        /**Botón para crear el personaje*/
    @FXML
    private Button crearButton;
    
    /**Botón para cancelar la creación*/
    @FXML
    private Button cancelarButton;
    
    /**Botón para salir de la aplicación*/
    @FXML
    private Button salirButton;
    
    /**Botón para incrementar vida máxima*/
    @FXML
    private Button incrementarVidaButton;
    
    /**Botón para decrementar vida máxima*/
    @FXML
    private Button decrementarVidaButton;
    
    /**Botón para incrementar ataque*/
    @FXML
    private Button incrementarAtaqueButton;
    
    /**Botón para decrementar ataque*/
    @FXML
    private Button decrementarAtaqueButton;
    
    /**Botón para incrementar defensa*/
    @FXML
    private Button incrementarDefensaButton;
    
    /**Botón para decrementar defensa*/
    @FXML
    private Button decrementarDefensaButton;
    
    /**Botón para incrementar velocidad*/
    @FXML
    private Button incrementarVelocidadButton;
    
    /**Botón para decrementar velocidad*/
    @FXML
    private Button decrementarVelocidadButton; 

    /**Atributos del héroe y configuración inicial*/
    /**Vida máxima inicial del héroe*/
    private int vidaMaxima = 100; 
    /**Ataque inicial del héroe*/      
    private int ataque = 10;
    /**Defensa inicial del héroe*/            
    private int defensa = 10; 
    /**Velocidad inicial del héroe*/          
    private int velocidad = 10; 
    /**Nivel inicial del héroe*/        
    private int nivel = 1;
    /**Puntos máximos para distribuir entre atributos*/              
    private int maxPuntos = 20;         
    /**Puntos restantes a distribuir*/
    private int puntosRestantes = maxPuntos; 

    /**Métodos para incrementar y decrementar atributos del héroe*/

    /**
     * Aumenta la vida máxima si hay puntos restantes.
     */
    @FXML
    private void incrementarVidaMaxima() {
        if (puntosRestantes > 0) {
            vidaMaxima++;
            puntosRestantes--;
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
        actualizarLabels(); /**Actualiza las etiquetas de la interfaz*/
    }

    /**
     * Reduce la vida máxima si es mayor a 1 y hay puntos para redistribuir.
     */
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

    /**
     * Aumenta el ataque si hay puntos restantes.
     */
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

    /**
     * Reduce el ataque si es mayor a 1 y hay puntos para redistribuir.
     */
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

    /**
     * Aumenta la defensa si hay puntos restantes.
     */
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

    /**
     * Reduce la defensa si es mayor a 1 y hay puntos para redistribuir.
     */
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

    /**
     * Aumenta la velocidad si hay puntos restantes.
     */
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

    /**
     * Reduce la velocidad si es mayor a 1 y hay puntos para redistribuir.
     */
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

    /**
     * Aumenta el nivel del héroe si no supera el límite.
     */
    @FXML
    private void incrementarNivel() {
        if (nivel < 100) {
            nivel++;
            actualizarLabels();
        } else {
            errorLabel.setText("El nivel no puede ser mayor a 100.");
        }
        actualizarLabels();
    }

    /**
     * Reduce el nivel del héroe si es mayor a 1.
     */
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


    /**
     * Actualiza todas las etiquetas con los valores actuales de los atributos.
     */
    private void actualizarLabels() {
        vidaMaximaLabel.setText("Vida Máxima: " + vidaMaxima);
        ataqueLabel.setText("Ataque: " + ataque);
        defensaLabel.setText("Defensa: " + defensa);
        velocidadLabel.setText("Velocidad: " + velocidad);
        puntosRestantesLabel.setText("Puntos Restantes: " + puntosRestantes);
        errorLabel.setText(""); // Limpia el mensaje de error
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

            Heroe heroe = new Heroe(nombre, 0, 0, vidaMaxima, ataque, defensa, velocidad, nivel);
            if (heroe != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mapaTest.fxml"));
                try {
                    Parent root = loader.load();
                    JuegoController juegoController = loader.getController();
                    juegoController.recibirHeroe(heroe);
                    
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
     * Restablece los valores iniciales de los atributos y limpia el campo de nombre.
     */
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

        // Inicializar valores por defecto
        actualizarLabels();
        errorLabel.setText("");
    }
}