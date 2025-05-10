package com.mazmorras.controllers;

// Importación de clases necesarias para la funcionalidad del controlador
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

    //Campos de la interfaz gráfica vinculados con la vista FXML
    @FXML
    private TextField nombreField;      //Campo para ingresar el nombre del héroe
    @FXML
    private Label vidaMaximaLabel;      //Etiqueta para mostrar la vida máxima
    @FXML
    private Label ataqueLabel;          //Etiqueta para mostrar el ataque
    @FXML
    private Label defensaLabel;         //Etiqueta para mostrar la defensa
    @FXML
    private Label velocidadLabel;       //Etiqueta para mostrar la velocidad
    @FXML
    private Label errorLabel;           //Etiqueta para mostrar mensajes de error
    @FXML
    private Label puntosRestantesLabel; //Etiqueta para mostrar los puntos restantes

    //Atributos del héroe y configuración inicial
    private int vidaMaxima = 100;       //Vida máxima inicial del héroe
    private int ataque = 10;            //Ataque inicial del héroe
    private int defensa = 10;           //Defensa inicial del héroe
    private int velocidad = 10;         //Velocidad inicial del héroe
    private int nivel = 1;              //Nivel inicial del héroe
    private int maxPuntos = 20;         //Puntos máximos para distribuir entre atributos

    private int puntosRestantes = maxPuntos; // Puntos restantes a distribuir

    //Métodos para incrementar y decrementar atributos del héroe

    //Aumenta la vida máxima si hay puntos restantes
    @FXML
    private void incrementarVidaMaxima() {
        if (puntosRestantes > 0) {
            vidaMaxima++;
            puntosRestantes--;
        } else {
            errorLabel.setText("No puedes incrementar más atributos.");
        }
        actualizarLabels(); //Actualiza las etiquetas de la interfaz
    }

    //Reduce la vida máxima si es mayor a 1 y hay puntos disponibles a redistribuir
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

    //Aumenta el ataque si hay puntos restantes
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

    //Reduce el ataque si es mayor a 1 y hay puntos disponibles para redistribuir
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

    //Aumenta la defensa si hay puntos restantes
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

    //Reduce la defensa si es mayor a 1 y hay puntos disponibles para redistribuir
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

    //Aumenta la velocidad si hay puntos restantes
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

    //Reduce la velocidad si es mayor a 1 y hay puntos disponibles para redistribuir
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

    //Aumenta el nivel si no supera el máximo permitido (100)
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

    //Reduce el nivel si es mayor a 1
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

    //Actualiza las etiquetas de la interfaz con los valores actuales de los atributos
    private void actualizarLabels() {
        vidaMaximaLabel.setText("Vida Máxima: " + vidaMaxima);
        ataqueLabel.setText("Ataque: " + ataque);
        defensaLabel.setText("Defensa: " + defensa);
        velocidadLabel.setText("Velocidad: " + velocidad);
        puntosRestantesLabel.setText("Puntos Restantes: " + puntosRestantes);
        errorLabel.setText(""); // Limpia el mensaje de error
    }

    //Crea un héroe con los atributos configurados y cambia a la siguiente escena
    //Obtiene el nombre del héroe desde el campo de texto y valida que todos los campos sean válidos.
    //Si los datos son correctos, instancia un héroe, carga la siguiente escena y configura la ventana.
    //Muestra un mensaje de error si ocurre algún problema durante la validación o la carga de la escena.
    @FXML
    private void crearHeroe() {
        try {
            String nombre = nombreField.getText();

            //Valida los campos que hemos metido
            if (nombre.isEmpty() || vidaMaxima <= 0 || ataque <= 0 || defensa <= 0 || velocidad <= 0 || nivel <= 0) {
                throw new IllegalArgumentException("Todos los campos deben ser válidos y mayores a 0.");
            }

            //Crea el héroe
            Heroe heroe = new Heroe(nombre, 0, 0, vidaMaxima, ataque, defensa, velocidad, nivel);
            if (heroe != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mapaTest.fxml"));
                try {
                    Parent root = loader.load();
                    JuegoController juegoController = loader.getController();
                    juegoController.recibirHeroe(heroe);
                    
                    //Configura la ventana y cambio de escena
                    Stage stage = (Stage) nombreField.getScene().getWindow();
                    double width = stage.getWidth();
                    double height = stage.getHeight();
                    boolean isMaximized = stage.isMaximized();
                    
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

    // Restablece los valores iniciales y limpia los campos
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

    // Cierra la aplicación
    @FXML
    private void salirAplicacion() {
        System.exit(0);
    }

    // Inicializa los valores de las etiquetas al cargar la vista
    @FXML
    private void initialize() {
        actualizarLabels();
        errorLabel.setText(""); // Limpia el mensaje de error al iniciar
    }
}
