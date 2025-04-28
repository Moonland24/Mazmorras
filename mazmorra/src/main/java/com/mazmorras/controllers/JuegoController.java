
package com.mazmorras.controllers;

import com.mazmorras.models.*;
import com.mazmorras.utils.Utils;
import com.mazmorras.views.JuegoView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

public class JuegoController {
    private Juego juego;
    private JuegoView view;
    private Heroe heroe;
    private Mapa mapa;

    public JuegoController(Juego juego, JuegoView view) {
        this.juego = juego;
        this.view = view;
        inicializarJuego();
    }

    /**
     * Inicializa los componentes principales del juego
     */
    private void inicializarJuego() {
        // 1. Cargar mapa desde archivo (ejemplo básico)
        this.mapa = new Mapa(10, 10); // Dimensiones por defecto
        
        // 2. Crear héroe con stats iniciales
        this.heroe = new Heroe("Héroe", 0, 0, 100, 15, 10, 5, 1);
        
        // 3. Cargar enemigos según nivel
        cargarEnemigos(1); // Nivel 1
        
        // 4. Configurar observadores
        juego.addListener(change -> actualizarVista());
    }

    /**
     * Carga enemigos según el nivel del juego
     */
    private void cargarEnemigos(int nivel) {
        List<Enemigo> enemigos = null;
        try {
            enemigos = Utils.cargarDesdeJSON("enemigos.json");
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        for (Enemigo enemigo : enemigos) {
            if (enemigo.getNivel() == nivel) {
                mapa.colocarEnemigo(enemigo);
            }
        }
    }

    /**
     * Maneja el input del teclado para mover al héroe
     */
    public void manejarInputTeclado(KeyEvent event) {
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

        if (direccion != null) {
            heroe.mover(mapa, direccion);
            verificarCombate();
            moverEnemigos();
            actualizarVista();
        }
    }

    /**
     * Verifica si el héroe está en combate
     */
    private void verificarCombate() {
        for (Enemigo enemigo : mapa.getEnemigos()) {
            if (heroe.getX() == enemigo.getX() && heroe.getY() == enemigo.getY()) {
                iniciarCombate(enemigo);
                break;
            }
        }
    }

    /**
     * Lógica de combate entre el héroe y un enemigo
     */
    private void iniciarCombate(Enemigo enemigo) {
        // Fórmula simplificada de daño
        int danioHeroe = Math.max(1, heroe.getAtaque() - enemigo.getDefensa() / 2);
        int danioEnemigo = Math.max(1, enemigo.getAtaque() - heroe.getDefensa() / 2);

        enemigo.recibirDanio(danioHeroe);
        heroe.recibirDanio(danioEnemigo);

        if (enemigo.getVidaActual() <= 0) {
            mapa.eliminarEnemigo(enemigo);
        }

        if (heroe.getVidaActual() <= 0) {
            gameOver();
        }
    }

    /**
     * Mueve todos los enemigos en el mapa
     */
    private void moverEnemigos() {
        for (Enemigo enemigo : mapa.getEnemigos()) {
            enemigo.mover(mapa, heroe); // El héroe es el objetivo
        }
    }

    /**
     * Actualiza la vista con el estado actual del juego
     */
    private void actualizarVista() {
        view.actualizarTablero(mapa, heroe);
        view.actualizarStats(heroe);
        view.actualizarTurno(juego.getTurnoActual());
    }

    /**
     * Maneja el fin del juego
     */
    private void gameOver() {
        view.mostrarGameOver();
        // Lógica adicional para reiniciar o salir
    }
}







/**package com.mazmorras.controllers;

import com.mazmorras.models.Heroe;
import com.mazmorras.models.Juego;
import com.mazmorras.views.JuegoView;
import com.mazmorras.models.Enemigo;
import com.mazmorras.models.TipoEnemigo;

import javafx.application.Platform;
import javafx.scene.control.skin.TextInputControlSkin.Direction;

public class JuegoController {

    private Juego juego;
    private JuegoView view;
    Heroe heroe = new Heroe("Heroe", 0, 0, 0, 0, 0, 0, 0);
    //Utils -- Leyendo enemigos
    // recorro la lista de enemigos que me devuelve el utils
    // si es el primer nivel, un enemigo, sacas solo los enemigos de nivel 1

//     for(int i = 0; i < enemigos.size(); i++){
//         Enemigo enemigo = enemigos.get(i);
//         // filtrar los enemigos por nivel
//         if(enemigo.getNivel() == 1){
//             // añadir el enemigo a la lista de enemigos del juego
//             juego.addEnemigo(enemigo);
//         }
// }
}*/