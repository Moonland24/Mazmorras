
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

    /**
     * Inicializa los componentes principales del juego
     * 
     * @throws IOException
     */
    @FXML
    private void inicialize() throws IOException {
        // 1. Cargar mapa desde archivo (ejemplo básico)
        Mapa mapa = new Mapa();
        mapa = Utils.cargarMapaDesdeTxt(
                "C:\\Users\\jfco1\\Desktop\\Mazmorras\\mazmorra\\src\\main\\resources\\mapas\\nivel1.txt");
        // 2. Crear héroe con stats iniciales
        Heroe heroe = new Heroe("Heroe", mapa.getEntrada().getX(), mapa.getEntrada().getY(), 100, 10, 5, 3, 1);
        // 3. Colocar héroe en el mapa
        mapa.colocarHeroe(heroe);
        // 4. Cargar enemigos según el nivel del juego
        mapa.setEnemigos(cargarEnemigos(1)); // Cargar enemigos de nivel 1
        // 5. Colocar enemigos en el mapa
        for (Enemigo enemigo : mapa.getEnemigos()) {
            mapa.colocarEnemigo(enemigo);
        }
        // 6. Mostrar una vista para testeo
        // Crear una vista del mapa y agregarla al gamePane
    }

    /**
     * Carga enemigos según el nivel del juego
     */
    private List<Enemigo> cargarEnemigos(int nivel) {
        List<Enemigo> enemigos = null;
        try {
            enemigos = Utils.cargarDesdeJSON("enemigos.json");
            for (Enemigo enemigo : enemigos) {
                if (enemigo.getNivel() == nivel) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onGameOver'");
    }

    @FXML
    private void generarMapaDesdeFXML() {
        try {
            // Cargar el mapa desde un archivo
            Mapa mapa = Utils.cargarMapaDesdeTxt(
                    "C:\\Users\\jfco1\\Desktop\\Mazmorras\\mazmorra\\src\\main\\resources\\mapas\\nivel1.txt");

            // Limpiar el GridPane antes de generar el mapa
            mapaGrid.getChildren().clear();

            // Generar el mapa en el GridPane
            for (int i = 0; i < mapa.getAlto(); i++) {
                for (int j = 0; j < mapa.getAncho(); j++) {
                    Label celda = new Label();
                    celda.setMinSize(30, 30);
                    celda.setAlignment(Pos.CENTER);
                    celda.setStyle("-fx-border-color: black; -fx-font-size: 12;");

                    // Determinar el contenido de la celda
                    if (mapa.esObstaculo(i, j)) {
                        celda.setText("X"); // Obstáculo
                        celda.setStyle("-fx-background-color: gray; -fx-border-color: black;");
                    } else if (mapa.getEntrada() != null && mapa.getEntrada().getX() == i
                            && mapa.getEntrada().getY() == j) {
                        celda.setText("E"); // Entrada
                        celda.setStyle("-fx-background-color: green; -fx-border-color: black;");
                    } else if (mapa.getSalida() != null && mapa.getSalida().getX() == i
                            && mapa.getSalida().getY() == j) {
                        celda.setText("S"); // Salida
                        celda.setStyle("-fx-background-color: red; -fx-border-color: black;");
                    } else {
                        celda.setText("."); // Camino
                        celda.setStyle("-fx-background-color: white; -fx-border-color: black;");
                    }

                    // Agregar la celda al GridPane
                    mapaGrid.add(celda, j, i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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