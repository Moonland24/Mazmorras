package com.mazmorras.controllers;

import com.mazmorras.enums.Direccion;
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

import java.io.IOException;
import java.util.List;

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
     */
    @FXML
    private void initialize() throws IOException {
        mapa = Utils.cargarMapaDesdeTxt(
                "C:\\Users\\jfco1\\Desktop\\Mazmorras\\mazmorra\\src\\main\\resources\\mapas\\nivel1.txt");
        if (mapa == null) {
            System.out.println("El mapa no se pudo cargar.");
            return;
        }
        generarMapaDesdeFXML(mapa);
        System.out.println("Inicialización completada. Esperando al héroe...");
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
     */
    private List<Enemigo> cargarEnemigos(int nivel) {
        List<Enemigo> enemigos = null;
        try {
            enemigos = Utils.cargarDesdeJSON(
                    "C:\\Users\\jfco1\\Desktop\\Mazmorras\\mazmorra\\src\\main\\resources\\enemigos\\enemigos.json");
            for (Enemigo enemigo : enemigos) {
                if (enemigo.getNivel() == nivel) {
                    // Colocar enemigos en el mapa según su nivel
                    // Aquí puedes agregar lógica para colocar enemigos en el mapa
                    // Por ejemplo, puedes usar un método en la clase Mapa para colocarlos
                    enemigos.add(enemigo);
                }
            }
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return enemigos;
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

    @FXML
    private void generarMapaDesdeFXML(Mapa mapa) {
        if (mapa == null) {
            System.out.println("El mapa no está inicializado.");
            return;
        }

        // Limpia el GridPane antes de generar el mapa
        mapaGrid.getChildren().clear();

        // Carga las imágenes necesarias
        Image imagenPared = new Image(getClass().getResourceAsStream("/imagenes/pared.png"));
        Image imagenPuerta = new Image(getClass().getResourceAsStream("/imagenes/puerta.png"));
        Image imagenCharco = new Image(getClass().getResourceAsStream("/imagenes/charco.png"));
        Image imagenBarril = new Image(getClass().getResourceAsStream("/imagenes/barril.png"));
        Image imagenSuelo = new Image(getClass().getResourceAsStream("/imagenes/suelo.png"));
        Image imagenPuertaAbierta = new Image(getClass().getResourceAsStream("/imagenes/puertaAbierta.png"));

        // Itera sobre las celdas del mapa
        for (int i = 0; i < mapa.getAlto(); i++) {
            for (int j = 0; j < mapa.getAncho(); j++) {
                char contenido = mapa.getContenido(i, j); // Obtén el contenido de la celda
                ImageView celdaImagen = new ImageView();
                
                // Asigna la imagen correspondiente según el contenido
                switch (contenido) {
                    case '#': // Pared
                        celdaImagen.setImage(imagenPared);
                        break;
                    case 'P': // Puerta
                        celdaImagen.setImage(imagenPuerta);
                        break;
                    case 'C': // Charco
                        celdaImagen.setImage(imagenCharco);
                        break;
                    case 'B': // Barril
                        celdaImagen.setImage(imagenBarril);
                        break;
                    case '.': // Suelo
                        celdaImagen.setImage(imagenSuelo);
                        break;
                    case 'S': // Puerta abierta
                        celdaImagen.setImage(imagenPuertaAbierta);
                        break;
                    default:
                        System.out.println("Contenido desconocido: " + contenido);
                        continue;
                }

                // Ajusta el tamaño de la imagen
                celdaImagen.setFitWidth(30);
                celdaImagen.setFitHeight(30);

                // Agrega la imagen al GridPane
                mapaGrid.add(celdaImagen, j, i);
            }
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