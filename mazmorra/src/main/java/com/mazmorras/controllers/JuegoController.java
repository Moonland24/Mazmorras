package com.mazmorras.controllers;

import com.mazmorras.enums.Direccion;
import com.mazmorras.interfaces.JuegoObserver;
import com.mazmorras.models.*;
import com.mazmorras.utils.Utils;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
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
    AnchorPane statsPane; // Referencia al pane de estadísticas
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
    private GridPane mapaGrid;

    private Mapa mapa = new Mapa();// Referencia al mapa

    /**
     * Inicializa los componentes principales del juego
     * 
     * @throws IOException
     * @throws URISyntaxException
     * @throws ParseException
     */
    @FXML
    private void initialize() throws IOException, URISyntaxException, ParseException {
        System.out.println("Inicializando JuegoController...");
        // Obtener la URL del recurso
        URL mapaUrl = getClass().getResource("/mapas/nivel1.txt");
        URL enemigosUrl = getClass().getResource("/enemigos/enemigos.json");

        if (mapaUrl == null || enemigosUrl == null) {
            System.err.println("No se pudo encontrar el archivo");
            return;
        }

        // Crear el Path y cargar el mapa
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

        // Esperar a que la escena esté disponible
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::onKeyPressed);
            }
        });
    }

    /**
     * Maneja el input del teclado para mover al héroe
     */
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
                moverEnemigos(); // Mueve a los enemigos después de que el héroe se mueve
                generarMapaDesdeFXML(mapa); // Actualiza el mapa visualmente
            } catch (Exception e) {
                System.out.println("Movimiento inválido: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica si el héroe está en combate
     */
    @Override
    public void onCombateCercano(Heroe heroe, Enemigo enemigo) {
        System.out.println("¡El héroe " + heroe.getNombre() + " está cerca del enemigo " + enemigo.getNombre() + "!");
        if (heroe.getX() == enemigo.getX() && heroe.getY() == enemigo.getY()) {
            System.out.println("¡El héroe y el enemigo están en la misma posición! Iniciando combate...");
            onCombateIniciado(heroe, enemigo);
        } else {
            System.out.println("El héroe y el enemigo están cerca, pero no en la misma posición.");
        }
    }

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
        // Asegurar que el AnchorPane capture los eventos de teclado
        rootPane.setFocusTraversable(true); // <-- Asegura que puede recibir foco
        rootPane.requestFocus();
        System.out.println("Héroe recibido: " + heroe.getNombre());
    }

    @Override
    public void onJuegoActualizado(Mapa mapa) {
        System.out.println("El juego ha sido actualizado.");
        generarMapaDesdeFXML(mapa);
    }

    @Override
    public void onHeroeMovido(Heroe heroe) {
        System.out.println("El héroe se ha movido a la posición: (" + heroe.getX() + ", " + heroe.getY() + ")");
        generarMapaDesdeFXML(mapa);
    }

    @Override
    public void onEnemigoActualizado(Enemigo enemigo, boolean eliminado) {
        if (eliminado) {
            System.out.println("El enemigo " + enemigo.getNombre() + " ha sido eliminado.");
        } else {
            System.out.println("El enemigo " + enemigo.getNombre() + " ha sido actualizado.");
        }
        generarMapaDesdeFXML(mapa);
    }

    @Override
    public void onCombateIniciado(Heroe heroe, Enemigo enemigo) {
        System.out.println(
                "¡Combate iniciado entre el héroe " + heroe.getNombre() + " y el enemigo " + enemigo.getNombre() + "!");

        while (heroe.getVidaActual() > 0 && enemigo.getVidaActual() > 0) {
            // Determinar quién ataca primero según la velocidad
            if (enemigo.getVelocidad() > heroe.getVelocidad()) {
                // Enemigo ataca primero
                System.out.println("El enemigo " + enemigo.getNombre() + " ataca primero.");
                enemigo.atacar(heroe);
                System.out.println("El enemigo ataca. Vida del héroe: " + heroe.getVidaActual());
                if (heroe.estaDerrotado()) {
                    System.out.println("¡El héroe ha sido derrotado!");
                    onGameOver(false);
                    break;
                }
                // Si el héroe sigue vivo, ataca
                heroe.atacar(enemigo);
                System.out.println("El héroe ataca. Vida del enemigo: " + enemigo.getVidaActual());
                if (enemigo.estaDerrotado()) {
                    System.out.println("¡El héroe ha derrotado al enemigo " + enemigo.getNombre() + "!");
                    mapa.eliminarEnemigo(enemigo);
                    onEnemigoActualizado(enemigo, true);
                    if (mapa.getEnemigos().isEmpty()) {
                        System.out.println("¡El héroe ha ganado el combate!");
                        onGameOver(true);
                    }
                    break;
                }
            } else {
                // Héroe ataca primero
                System.out.println("El héroe " + heroe.getNombre() + " ataca primero.");
                heroe.atacar(enemigo);
                System.out.println("El héroe ataca. Vida del enemigo: " + enemigo.getVidaActual());
                if (enemigo.estaDerrotado()) {
                    System.out.println("¡El héroe ha derrotado al enemigo " + enemigo.getNombre() + "!");
                    mapa.eliminarEnemigo(enemigo);
                    onEnemigoActualizado(enemigo, true);
                    if (mapa.getEnemigos().isEmpty()) {
                        System.out.println("¡El héroe ha ganado el combate!");
                        onGameOver(true);
                    }
                    break;
                }
                // Si el enemigo sigue vivo, ataca
                enemigo.atacar(heroe);
                System.out.println("El enemigo ataca. Vida del héroe: " + heroe.getVidaActual());
                if (heroe.estaDerrotado()) {
                    System.out.println("¡El héroe ha sido derrotado!");
                    onGameOver(false);
                    break;
                }
            }
        }
    }

    @Override
    public void onGameOver(boolean victoria) {

        if (victoria) {
            // Mostrar el pane de victoria
            gamePane.setVisible(false);
            victoryPane.setVisible(true);
        } else {
            // Mostrar el pane de Game Over
            gamePane.setVisible(false);
            gameOverPane.setVisible(true);
        }
    }

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
            if(enemigo.estaEnRango(mapa.getHeroe(), enemigo.getPercepcion())) {
                onCombateCercano(mapa.getHeroe(), enemigo);
            }
        }
        generarMapaDesdeFXML(mapa); // Solo una vez al final
    }

    @FXML
    private void generarMapaDesdeFXML(Mapa mapa) {
        if (mapa == null) {
            System.out.println("El mapa no está inicializado.");
            return;
        }

        mapaGrid.getChildren().clear();

        // Inicializar cache de imágenes si no existe
        if (imageCache == null) {
            inicializarImagenes();
        }

        // Generar caminos
        for (Camino camino : mapa.getCaminos()) {
            ImageView celda = crearCelda(imageCache.get("suelo"));
            mapaGrid.add(celda, camino.getX(), camino.getY());
        }

        // Generar Enemigos
        for (Enemigo enemigo : mapa.getEnemigos()) {
            ImageView celdaEnemigo = crearCelda(imageCache.get(enemigo.getNombre().toLowerCase()));
            mapaGrid.add(celdaEnemigo, enemigo.getX(), enemigo.getY());
        }

        // Generar obstáculos
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
    }

    // manejar Eventos de teclado para mover al héroe
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
        event.consume(); // Evitar que el evento se propague
        rootPane.requestFocus(); // Asegurar que el foco regrese al AnchorPane
        System.out.println("Evento consumido.");
    }

}

/**
 * package com.mazmorras.controllers;
 * 
 * import com.mazmorras.models.Heroe;
 * import com.mazmorras.models.Juego;
 * import com.mazmorras.views.JuegoView;
 * import com.mazmorras.models.Enemigo;
 * import com.mazmorras.models.TipoEnemigo;
 * 
 * import javafx.application.Platform;
 * import javafx.scene.control.skin.TextInputControlSkin.Direction;
 * 
 * public class JuegoController {
 * 
 * private Juego juego;
 * private JuegoView view;
 * Heroe heroe = new Heroe("Heroe", 0, 0, 0, 0, 0, 0, 0);
 * //Utils -- Leyendo enemigos
 * // recorro la lista de enemigos que me devuelve el utils
 * // si es el primer nivel, un enemigo, sacas solo los enemigos de nivel 1
 * 
 * // for(int i = 0; i < enemigos.size(); i++){
 * // Enemigo enemigo = enemigos.get(i);
 * // // filtrar los enemigos por nivel
 * // if(enemigo.getNivel() == 1){
 * // // añadir el enemigo a la lista de enemigos del juego
 * // juego.addEnemigo(enemigo);
 * // }
 * // }
 * }
 */