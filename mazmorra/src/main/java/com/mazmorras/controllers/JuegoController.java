package com.mazmorras.controllers;

// Importación de clases necesarias para la funcionalidad del controlador
import com.mazmorras.enums.Direccion;
import com.mazmorras.interfaces.JuegoObserver;
import com.mazmorras.models.*;
import com.mazmorras.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

public class JuegoController implements JuegoObserver {
    @FXML
    AnchorPane rootPane; // Referencia al pane principal de la vista
    @FXML
    AnchorPane gamePane; // Referencia al pane del juego
    @FXML
    GridPane statsPane; // Referencia al pane de estadísticas
    @FXML
    AnchorPane enemiesPane; // Referencia al pane de enemigos
    @FXML
    AnchorPane gameOverPane; // Referencia al pane de Game Over
    @FXML
    AnchorPane victoryPane; // Referencia al pane de Victoria
    @FXML
    AnchorPane pausePane; // Referencia al pane de Pausa
    @FXML
    AnchorPane menuPane; // Referencia al pane del menú
    @FXML
    Label vidaLabel; // Etiqueta para mostrar la vida del héroe
    @FXML
    Label ataqueLabel; // Etiqueta para mostrar el ataque del héroe
    @FXML
    Label defensaLabel; // Etiqueta para mostrar la defensa del héroe
    @FXML
    Label velocidadLabel; // Etiqueta para mostrar la velocidad del héroe
    @FXML
    Label textoFinal; // Texto final que se muestra al finalizar el juego
    @FXML
    Button volverJugar;
    @FXML
    Label turnoInfo; // Etiqueta para mostrar información del turno
    @FXML
    Button salir;
    @FXML
    Label turnoLabel;

    @FXML
    private GridPane mapaGrid;

    private Mapa mapa = new Mapa();// Referencia al mapa

    private int turno = 1;

    // Inicializa los componentes principales del juego
    @FXML
    private void initialize() throws IOException, URISyntaxException, ParseException {
        System.out.println("Inicializando JuegoController...");
        // Obtiene la URL del recurso
        URL mapaUrl = getClass().getResource("/mapas/nivel1.txt");
        URL enemigosUrl = getClass().getResource("/enemigos/enemigos.json");

        if (mapaUrl == null || enemigosUrl == null) {
            System.err.println("No se pudo encontrar el archivo");
            return;
        }

        // Crea el Path y carga el mapa
        Path mapaPath = Paths.get(mapaUrl.toURI());
        Path enemigosPath = Paths.get(enemigosUrl.toURI());
        List<Enemigo> enemigos = Utils.cargarDesdeJSON(enemigosPath.toString());

        try (InputStream inputStream = Files.newInputStream(mapaPath)) {
            mapa = Utils.cargarMapaDesdeTxt(inputStream, enemigos, 1);
            if (mapa == null) {
                System.out.println("El mapa no se pudo cargar.");
                return;
            }
            System.out.println("Inicialización completada. Esperando al héroe...");
        }

        if (mapa == null) {
            System.out.println("El mapa no se pudo inicializar.");
        } else {
            System.out.println("Mapa inicializado correctamente.");
        }

        // Espera a que la escena esté disponible
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::onKeyPressed);
            }
        });
    }

    // Maneja el input del teclado para mover al héroe
    public void manejarInputTeclado(KeyEvent event, Heroe heroe) {
        KeyCode tecla = event.getCode();
        Direccion direccion = null;

        if (tecla == KeyCode.W || tecla == KeyCode.UP) {
            direccion = Direccion.ARRIBA;
        } else if (tecla == KeyCode.S || tecla == KeyCode.DOWN) {
            direccion = Direccion.ABAJO;
        } else if (tecla == KeyCode.A || tecla == KeyCode.LEFT) {
            direccion = Direccion.IZQUIERDA;
        } else if (tecla == KeyCode.D || tecla == KeyCode.RIGHT) {
            direccion = Direccion.DERECHA;
        }

        if (direccion != null) {
            try {
                heroe.mover(mapa, direccion); // Mueve al héroe

                // Comprobar si el héroe está en la salida
                Salida salida = mapa.getSalida();
                if (salida != null && heroe.getX() == salida.getX() && heroe.getY() == salida.getY()) {
                    onGameOver(true);
                    return; // Termina aquí para que no sigan los turnos
                }

                moverEnemigos(); // Mueve a los enemigos después de que el héroe se mueve
                generarMapaDesdeFXML(mapa); // Actualiza el mapa visualmente
                turno++; // Incrementa el turno
                turnoLabel.setText("Turno: " + turno); // Actualiza el label
            } catch (Exception e) {
                System.out.println("Movimiento inválido: " + e.getMessage());
            }
        }
    }

    // Verifica si el héroe está en combate
    @Override
    public void onCombateCercano(Heroe heroe, Enemigo enemigo) {
        System.out.println("¡El héroe " + heroe.getNombre() + " está cerca del enemigo " + enemigo.getNombre() + "!");
        turnoInfo.setText("¡Pelea!");
        if (heroe.getX() == enemigo.getX() && heroe.getY() == enemigo.getY()) {
            System.out.println("¡El héroe y el enemigo están en la misma posición! Iniciando combate...");
            turnoInfo.setText("¡Combatiendo!");
            onCombateIniciado(heroe, enemigo);
        } else {
            System.out.println("El héroe y el enemigo están cerca, pero no en la misma posición.");
            turnoInfo.setText("¡Pelea!");
        }
    }

    // Recibe al heroe en el mapa
    public void recibirHeroe(Heroe heroe) {
        System.out.println("Recibiendo héroe...");
        if (heroe == null) {
            System.out.println("El héroe recibido es null.");
            return;
        }

        if (mapa == null) {
            System.out.println("El mapa no está inicializado.");
            return; // Evita el NullPointerException
        }
        Entrada entrada = mapa.getEntrada();
        if (entrada != null) {
            heroe.setX(entrada.getX());
            heroe.setY(entrada.getY());
        }
        mapa.setHeroe(heroe);

        generarMapaDesdeFXML(mapa);
        // Asegura que el AnchorPane captura los eventos de teclado
        rootPane.setFocusTraversable(true); // <-- Asegura que puede recibir foco
        rootPane.requestFocus();
        System.out.println("Héroe recibido: " + heroe.getNombre());
    }

    // Actualiza el juego
    @Override
    public void onJuegoActualizado(Mapa mapa) {
        System.out.println("El juego ha sido actualizado.");
        generarMapaDesdeFXML(mapa);
    }

    // Retorna el sitio al que se ha movido el heroe
    @Override
    public void onHeroeMovido(Heroe heroe) {
        System.out.println("El héroe se ha movido a la posición: (" + heroe.getX() + ", " + heroe.getY() + ")");
        turnoInfo.setText("Héroe movido a la posición: (" + heroe.getX() + ", " + heroe.getY() + ")");
        generarMapaDesdeFXML(mapa);
    }

    // Indica si el enemigo ha sido eliminado, y si no lo actualiza generando otro
    @Override
    public void onEnemigoActualizado(Enemigo enemigo, boolean eliminado) {
        if (eliminado) {
            System.out.println("El enemigo " + enemigo.getNombre() + " ha sido eliminado.");
            turnoInfo.setText("Enemigo " + enemigo.getNombre() + " eliminado.");
        } else {
            System.out.println("El enemigo " + enemigo.getNombre() + " ha sido actualizado.");
            turnoInfo.setText("Enemigo " + enemigo.getNombre() + " actualizado.");
        }
        generarMapaDesdeFXML(mapa);
    }

    // Inicia el combate entre héroe y enemigo
    @Override
    public void onCombateIniciado(Heroe heroe, Enemigo enemigo) {
        System.out.println(
                "¡Combate iniciado entre el héroe " + heroe.getNombre() + " y el enemigo " + enemigo.getNombre() + "!");
                turnoInfo.setText("¡Combate!");

        // Simulación básica de combate
        while (heroe.getVidaActual() > 0 && enemigo.getVidaActual() > 0) {
            // Determinar quién ataca primero según la velocidad
            if (enemigo.getVelocidad() > heroe.getVelocidad()) {
                // Enemigo ataca primero
                System.out.println("El enemigo " + enemigo.getNombre() + " ataca primero.");
                turnoInfo.setText("Enemigo " + enemigo.getNombre() + " ataca primero.");
                enemigo.atacar(heroe);
                System.out.println("El enemigo ataca. Vida del héroe: " + heroe.getVidaActual());
                turnoInfo.setText("Enemigo ataca. Vida del héroe: " + heroe.getVidaActual());
                if (heroe.estaDerrotado()) {
                    System.out.println("¡El héroe ha sido derrotado!");
                    onGameOver(false);

                    break;
                }
                // Si el héroe sigue vivo, ataca
                heroe.atacar(enemigo);
                System.out.println("El héroe ataca. Vida del enemigo: " + enemigo.getVidaActual());
                turnoInfo.setText("Héroe ataca. Vida del enemigo: " + enemigo.getVidaActual());
                if (enemigo.estaDerrotado()) {
                    System.out.println("¡El héroe ha derrotado al enemigo " + enemigo.getNombre() + "!");
                    turnoInfo.setText("¡Héroe ha derrotado al enemigo " + enemigo.getNombre() + "!");
                    mapa.eliminarEnemigo(enemigo);
                    onEnemigoActualizado(enemigo, true);
                    if (mapa.getEnemigos().isEmpty()) {
                        System.out.println("¡El héroe ha ganado el combate!");
                        turnoInfo.setText("¡Héroe ha ganado el combate!");
                        onGameOver(true);
                    }
                    break;
                }
                System.out.println("El enemigo ataca. Vida del héroe: " + heroe.getVidaActual()); // Va retornando la
                                                                                                 // vida que le queda
                                                                                                  // al heroe
            } else {
                // Héroe ataca primero
                System.out.println("El héroe " + heroe.getNombre() + " ataca primero.");
                turnoInfo.setText("Héroe " + heroe.getNombre() + " ataca primero.");
                heroe.atacar(enemigo);
                // Si el enemigo es derrotado se canta victoria
                System.out.println("El héroe ataca. Vida del enemigo: " + enemigo.getVidaActual());
                turnoInfo.setText("Héroe ataca. Vida del enemigo: " + enemigo.getVidaActual());
                if (enemigo.estaDerrotado()) {
                    System.out.println("¡El héroe ha derrotado al enemigo " + enemigo.getNombre() + "!");
                    turnoInfo.setText("¡Héroe ha derrotado al enemigo " + enemigo.getNombre() + "!");
                    mapa.eliminarEnemigo(enemigo);
                    onEnemigoActualizado(enemigo, true);
                    if (mapa.getEnemigos().isEmpty()) {
                        System.out.println("¡El héroe ha ganado el combate!");
                        turnoInfo.setText("¡Héroe ha ganado el combate!");
                        onGameOver(true);
                    }
                    break;
                }
                // Si el enemigo sigue vivo, ataca
                enemigo.atacar(heroe);
                System.out.println("El enemigo ataca. Vida del héroe: " + heroe.getVidaActual());
                turnoInfo.setText("Enemigo ataca. Vida del héroe: " + heroe.getVidaActual());
                if (heroe.estaDerrotado()) {
                    System.out.println("¡El héroe ha sido derrotado!");
                    turnoInfo.setText("¡Héroe derrotado!");
                    onGameOver(false);
                    break;
                }
                System.out.println("El héroe ataca. Vida del enemigo: " + enemigo.getVidaActual()); // Va retornando la
                                                                                                    // vida que le queda
                                                                                                    // al enemigo
            }

            // Verifica si el enemigo ha sido derrotado
            if (enemigo.getVidaActual() <= 0) {
                System.out.println("¡El enemigo " + enemigo.getNombre() + " ha sido derrotado!");
                mapa.eliminarEnemigo(enemigo);
                onEnemigoActualizado(enemigo, true);
                break;
            }
        }
    }

    // Se establece el resultado del combate ya sea victoria o gameover
    @Override
    public void onGameOver(boolean victoria) {
        System.out.println("Ejecutando onGameOver: " + (victoria ? "Victoria" : "Derrota"));
        
        if (victoria) {
            // Mostrar panel de victoria
            victoryPane.setVisible(true);
            gameOverPane.setVisible(false);
        } else {
            // Mostrar panel de game over
            victoryPane.setVisible(false);
            gameOverPane.setVisible(true);
        }

        // Asegurar que los paneles están por encima
        if (victoryPane != null) {
            victoryPane.toFront();
        }
        if (gameOverPane != null) {
            gameOverPane.toFront();
        }

        // Actualizar las estadísticas una última vez
        if (mapa != null && mapa.getHeroe() != null) {
            Heroe heroe = mapa.getHeroe();
            vidaLabel.setText("Vida: " + heroe.getVidaActual());
            ataqueLabel.setText("Ataque: " + heroe.getAtaque());
            defensaLabel.setText("Defensa: " + heroe.getDefensa());
            velocidadLabel.setText("Velocidad: " + heroe.getVelocidad());
        }
    }

    // Maneja el evento de volver a jugar
    @FXML
    private void volverJugar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/creacionpersonaje.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/creacionpersonaje.css").toExternalForm());
        Stage stage = (Stage) volverJugar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Maneja el evento de salir del juego
    @FXML
    private void salirJuego() {
        System.out.println("Saliendo del juego...");
        Stage stage = (Stage) salir.getScene().getWindow();
        stage.close(); // Cierra la ventana
    }

    // Mapa que inicializa todas las imagenes que componen al juego
    private Map<String, Image> imageCache;

    private void inicializarImagenes() {
        imageCache = new HashMap<>();
        imageCache.put("pared", new Image(getClass().getResourceAsStream("/imagenes/pared.png")));
        imageCache.put("puerta", new Image(getClass().getResourceAsStream("/imagenes/puerta.png")));
        imageCache.put("charco", new Image(getClass().getResourceAsStream("/imagenes/charco.png")));
        imageCache.put("barril", new Image(getClass().getResourceAsStream("/imagenes/barril.png")));
        imageCache.put("suelo", new Image(getClass().getResourceAsStream("/imagenes/suelo.png")));
        imageCache.put("puertaAbierta", new Image(getClass().getResourceAsStream("/imagenes/puertaAbierta.png")));
        imageCache.put("aranha", new Image(getClass().getResourceAsStream("/imagenes/aranha.png")));
        imageCache.put("goblin", new Image(getClass().getResourceAsStream("/imagenes/goblin.png")));
        imageCache.put("esqueleto", new Image(getClass().getResourceAsStream("/imagenes/esqueleto.png")));
        imageCache.put("heroe", new Image(getClass().getResourceAsStream("/imagenes/heroe.png")));
    }

    // Se crean las celdas donde va a ir cada elemento del mapa (héroe, obstaculos,
    // etc)
    private ImageView crearCelda(Image imagen) {
        ImageView celda = new ImageView(imagen);
        celda.setFitWidth(30);
        celda.setFitHeight(30);
        return celda;
    }

    // Sistema de turnos
    private void moverEnemigos() {
        for (Enemigo enemigo : mapa.getEnemigos()) {
            enemigo.moverEnemigo(mapa, mapa.getHeroe());
            if (enemigo.estaEnRango(mapa.getHeroe(), enemigo.getPercepcion())) {
                onCombateCercano(mapa.getHeroe(), enemigo);
            }
        }
        generarMapaDesdeFXML(mapa); // Solo una vez al final
    }

    // Se genera el mapa desde el FXML y le carga las imagenes dandole la posicion
    // establecida en el archivo de dayos donde se encuentra el mapa
    @FXML
    private void generarMapaDesdeFXML(Mapa mapa) {
        if (mapa == null) {
            System.out.println("El mapa no está inicializado.");
            return;
        }

        mapaGrid.getChildren().clear();

        // Inicializa el cache de imágenes si no existe
        if (imageCache == null) {
            inicializarImagenes();
        }

        // Genera caminos
        for (Camino camino : mapa.getCaminos()) {
            ImageView celda = crearCelda(imageCache.get("suelo"));
            mapaGrid.add(celda, camino.getX(), camino.getY());
        }

        // Genera Enemigos
        for (Enemigo enemigo : mapa.getEnemigos()) {
            ImageView celdaEnemigo = crearCelda(imageCache.get(enemigo.getNombre().toLowerCase()));
            mapaGrid.add(celdaEnemigo, enemigo.getX(), enemigo.getY());
        }

        // Genera obstáculos
        for (Obstaculo obstaculo : mapa.getObstaculos()) {
            String tipoImagen = null;
            switch (obstaculo.getTipoObstaculo()) {
                case BARRIL:
                    tipoImagen = "barril";
                    break;
                case PARED:
                    tipoImagen = "pared";
                    break;
                case CHARCO:
                    tipoImagen = "charco";
                    break;
                default:
                    System.out.println("No existe ese tipo de obstáculo");
                    continue;
            }
            ImageView celda = crearCelda(imageCache.get(tipoImagen));
            mapaGrid.add(celda, obstaculo.getX(), obstaculo.getY());
        }

        // Entrada, héroe y salida
        Entrada entrada = mapa.getEntrada();
        Salida salida = mapa.getSalida();
        Heroe heroe = mapa.getHeroe();

        if (entrada != null) {
            ImageView celdaEntrada = crearCelda(imageCache.get("puerta"));
            mapaGrid.add(celdaEntrada, entrada.getX(), entrada.getY());
        }
        if (heroe != null) {
            ImageView celdaHeroe = crearCelda(imageCache.get("heroe"));
            mapaGrid.add(celdaHeroe, heroe.getX(), heroe.getY());
        }
        if (salida != null) {
            ImageView celdaSalida = crearCelda(imageCache.get("puertaAbierta"));
            mapaGrid.add(celdaSalida, salida.getX(), salida.getY());
        }

        vidaLabel.setText("Vida: " + heroe.getVidaActual());
        ataqueLabel.setText("Ataque: " + heroe.getAtaque());
        defensaLabel.setText("Defensa: " + heroe.getDefensa());
        velocidadLabel.setText("Velocidad: " + heroe.getVelocidad());
    }

    // Maneja Eventos de teclado para mover al héroe
    @FXML
    private void onKeyPressed(KeyEvent event) {
        System.out.println("Tecla presionada: " + event.getCode());
        Heroe heroe = mapa.getHeroe();
        if (heroe != null) {
            manejarInputTeclado(event, heroe);
            generarMapaDesdeFXML(mapa); // Actualiza el mapa visualmente
        } else {
            System.out.println("El héroe no está inicializado.");
        }
        event.consume(); // Evita que el evento se propague
        rootPane.requestFocus(); // Asegura que el foco regrese al AnchorPane
        System.out.println("Evento consumido.");
    }

}