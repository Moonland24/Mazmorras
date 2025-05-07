package com.mazmorras.controllers;

import com.mazmorras.enums.Direccion;
import com.mazmorras.enums.TipoObstaculo;
import com.mazmorras.interfaces.JuegoObserver;
import com.mazmorras.models.*;
import com.mazmorras.utils.Utils;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     */
    @FXML
    private void initialize() throws IOException, URISyntaxException {
        // Obtener la URL del recurso
        URL resourceUrl = getClass().getResource("/mapas/nivel1.txt");
        if (resourceUrl == null) {
            System.err.println("No se pudo encontrar el archivo nivel1.txt");
            return;
        }

        // Crear el Path y cargar el mapa
        Path path = Paths.get(resourceUrl.toURI());

        // Primero leemos el contenido para mostrarlo
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        }

        // Luego cargamos el mapa con un nuevo InputStream
        try (InputStream inputStream = Files.newInputStream(path)) {
            mapa = Utils.cargarMapaDesdeTxt(inputStream);
            if (mapa == null) {
                System.out.println("El mapa no se pudo cargar.");
                return;
            }
            generarMapaDesdeFXML(mapa);
            System.out.println("Inicialización completada. Esperando al héroe...");
        }
    }

    // 3. Colocar héroe en el mapa
    public void colocarHeroeEnEntrada(Heroe heroe, Mapa mapa) {
        if (mapa == null) {
            System.out.println("El mapa no está inicializado.");
            return;
        }

        if (mapa.getEntrada() == null) {
            System.out.println("La entrada del mapa no está definida.");
            return;
        }

        if (heroe == null) {
            System.out.println("El héroe no está inicializado.");
            return;
        }

        if (mapaGrid == null) {
            System.out.println("El GridPane (mapaGrid) no está inicializado.");
            return;
        }

        // Colocar el héroe en la entrada del mapa
        heroe.setX(mapa.getEntrada().getX());
        heroe.setY(mapa.getEntrada().getY());
        Label celda = new Label("H"); // Representación del héroe
        celda.setMinSize(30, 30);
        celda.setAlignment(Pos.CENTER);
        celda.setStyle("-fx-border-color: black; -fx-font-size: 12; -fx-background-color: blue;");
        mapaGrid.add(celda, mapa.getEntrada().getY(), mapa.getEntrada().getX());
    }

    /**
     * Carga enemigos según el nivel del juego
     * 
     * @throws ParseException
     * @throws IOException
     */
    private List<Enemigo> cargarEnemigos(int nivel) throws IOException, ParseException {
        List<Enemigo> enemigos = new ArrayList<>();

        // Obtener la URL del recurso
        URL resourceUrl = getClass().getResource("/json/enemigos.json");
        if (resourceUrl == null) {
            System.err.println("No se pudo encontrar el archivo enemigos.json");
            return enemigos;
        }

        // Crear el Path y cargar los enemigos
        try (InputStream inputStream = resourceUrl.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            // Debug: Mostrar contenido del archivo
            String contenido = reader.lines().collect(Collectors.joining("\n"));
            System.out.println("Contenido del archivo:");
            System.out.println(contenido);

            // Cargar enemigos desde el JSON
            enemigos = Utils.cargarDesdeJSON(resourceUrl.getPath());

            // Filtrar enemigos por nivel
            return enemigos.stream()
                    .filter(e -> e.getNivel() == nivel)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Maneja el input del teclado para mover al héroe
     */
    public void manejarInputTeclado(KeyEvent event, Heroe heroe) {
        // Verifica si el evento es de teclado
        KeyCode tecla = event.getCode();
        Direccion direccion = null;

        // Mapear teclas a direcciones
        if (tecla == KeyCode.W || tecla == KeyCode.UP) {
            direccion = Direccion.ARRIBA;
        } else if (tecla == KeyCode.S || tecla == KeyCode.DOWN) {
            direccion = Direccion.ABAJO;
        } else if (tecla == KeyCode.A || tecla == KeyCode.LEFT) {
            direccion = Direccion.IZQUIERDA;
        } else if (tecla == KeyCode.D || tecla == KeyCode.RIGHT) {
            direccion = Direccion.DERECHA;
        }

        // if (direccion != null) {
        // heroe.mover(mapa, direccion);
        // onCombateCercano(null, null); // Verifica si hay combate cercano
        // onEnemigoActualizado(null, false);
        // moverEnemigos();
        // onJuegoActualizado(null); // Actualiza el juego
        // }
    }

    /**
     * Verifica si el héroe está en combate
     */
    @Override
    public void onCombateCercano(Heroe heroe, Enemigo enemigo) {
        // for (Enemigo enemigo : mapa.getEnemigos()) {
        // if (heroe.getX() == enemigo.getX() && heroe.getY() == enemigo.getY()) {
        // iniciarCombate(enemigo);
        // break;
        // }
        // }
    }

    public void recibirHeroe(Heroe heroe) {
        if (heroe == null) {
            System.out.println("El héroe recibido es null.");
            return;
        }
        System.out.println("Héroe recibido: " + heroe.getNombre());
        if (mapa != null) {
            colocarHeroeEnEntrada(heroe, mapa);
        } else {
            System.out.println("El mapa no está inicializado.");
        }
    }

    @Override
    public void onJuegoActualizado(Juego juego) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onJuegoActualizado'");
    }

    @Override
    public void onHeroeMovido(Heroe heroe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onHeroeMovido'");
    }

    @Override
    public void onEnemigoActualizado(Enemigo enemigo, boolean eliminado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnemigoActualizado'");
    }

    @Override
    public void onCombateIniciado(Heroe heroe, Enemigo enemigo) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCombateIniciado'");
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
        imageCache.put("enemigoTest", new Image(getClass().getResourceAsStream("/imagenes/enemigoTest.png")));
    }

    private ImageView crearCelda(Image imagen) {
        ImageView celda = new ImageView(imagen);
        celda.setFitWidth(30);
        celda.setFitHeight(30);
        return celda;
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
            mapaGrid.add(celda, camino.getY(), camino.getX());
        }

        //Generar Enemigos
        for (Enemigo enemigo: mapa.getEnemigos()){
            ImageView celda = crearCelda(imageCache.get("enemigoTest"));
            mapaGrid.add(celda, enemigo.getY(), enemigo.getX());
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
            mapaGrid.add(celda, obstaculo.getY(), obstaculo.getX());
        }

        // Generar entrada y salida
        Entrada entrada = mapa.getEntrada();
        Salida salida = mapa.getSalida();

        if (entrada != null) {
            ImageView celdaEntrada = crearCelda(imageCache.get("puerta"));
            mapaGrid.add(celdaEntrada, entrada.getY(), entrada.getX());
        }

        if (salida != null) {
            ImageView celdaSalida = crearCelda(imageCache.get("puertaAbierta"));
            mapaGrid.add(celdaSalida, salida.getY(), salida.getX());
        }
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