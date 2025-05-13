package com.mazmorras.models;

import com.mazmorras.enums.Direccion;
import com.mazmorras.enums.TipoEnemigo;

/**
 * Representa a un enemigo dentro del juego, heredando atributos y comportamientos
 * de la clase {@link Personaje}. Cada enemigo tiene un tipo específico y un nivel
 * de percepción que define su rango de detección.
 * 
 * La inteligencia artificial básica permite al enemigo perseguir al jugador si está
 * dentro del rango de percepción o moverse aleatoriamente en caso contrario.
 * 
 * @author Inma
 * @author Juanfran
 * @version 1.0
 */

public class Enemigo extends Personaje {
    /** Tipo específico del enemigo (por ejemplo: jefe, minion, etc.) */
    private TipoEnemigo tipo;
    /** Rango de percepción del enemigo: determina si puede detectar al jugador */
    private int percepcion;

    /**
     * Constructor para inicializar un enemigo con atributos definidos.
     *
     * @param nombre      Nombre del enemigo.
     * @param x           Posición X en el mapa.
     * @param y           Posición Y en el mapa.
     * @param vidaMaxima  Vida máxima del enemigo.
     * @param ataque      Nivel de ataque.
     * @param defensa     Nivel de defensa.
     * @param velocidad   Nivel de velocidad.
     * @param tipo        Tipo del enemigo según {@link TipoEnemigo}.
     * @param percepcion  Rango de percepción (distancia de detección).
     * @param nivel       Nivel del enemigo.
     */
    public Enemigo(String nombre, int x, int y, int vidaMaxima, int ataque,
            int defensa, int velocidad, TipoEnemigo tipo, int percepcion, int nivel) {
        super(nombre, x, y, vidaMaxima, ataque, defensa, velocidad, nivel);
        this.percepcion = percepcion; // Rango de percepción por defecto
        this.tipo = tipo;
    }

    public TipoEnemigo getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnemigo tipo) {
        this.tipo = tipo;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de tipo
    }

    public int getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de percepción
    }

    /**
     * Mueve al enemigo según su IA básica:
     * - Si el objetivo está dentro del rango de percepción, lo persigue.
     * - Si no, realiza un movimiento aleatorio válido.
     *
     * @param mapa     Referencia al mapa del juego.
     * @param objetivo Personaje objetivo (generalmente el jugador).
     */
    public void moverEnemigo(Mapa mapa, Personaje objetivo) {
        // IA simple: perseguir al jugador si está en rango
        if (estaEnRango(objetivo, this.percepcion)) {
            int[] nuevaPos = mapa.encontrarCamino(x, y, objetivo.getX(), objetivo.getY());
            if (nuevaPos != null

                    && nuevaPos[0] >= 0 && nuevaPos[0] < mapa.getAncho()
                    && nuevaPos[1] >= 0 && nuevaPos[1] < mapa.getAlto()
                    && mapa.isValidMove(nuevaPos[0], nuevaPos[1])) {
                setX(nuevaPos[0]);
                setY(nuevaPos[1]);
                // Notifica a los observadores del movimiento
                notifyPersonajeActualizado();
            }
        } else {
            // Movimiento aleatorio SOLO si es válido y dentro de los límites
            int[][] movimientos = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
            java.util.List<int[]> posibles = new java.util.ArrayList<>();
            for (int[] mov : movimientos) {
                int nx = x + mov[0];
                int ny = y + mov[1];
                if (nx >= 0 && nx < mapa.getAncho() && ny >= 0 && ny < mapa.getAlto()
                        && mapa.isValidMove(nx, ny)) {
                    posibles.add(new int[] { nx, ny });
                    notifyPersonajeActualizado();
                }
            }
            // Elegir un movimiento aleatorio de los posibles
            if (!posibles.isEmpty()) {
                int[] elegido = posibles.get((int) (Math.random() * posibles.size()));
                setX(elegido[0]);
                setY(elegido[1]);
                // Notifica a los observadores del movimiento
                notifyPersonajeActualizado();
            }
        }
    }

    /**
     * Método representativo que imprime un mensaje indicando que el enemigo
     * realiza un gruñido, útil para efectos de sonido o ambientación.
     */
    public void gritar() {
        System.out.println(nombre + " gruñe amenazadoramente!");
    }

    /**
     * Representación textual del enemigo para propósitos de depuración o interfaz.
     *
     * @return Cadena de texto con los atributos básicos del enemigo.
     */
    @Override
    public String toString() {
        return "Enemigo: " + getNombre() +
                " (Nivel " + getNivel() + ")" +
                " - Vida: " + getVidaActual() + "/" + getVidaMaxima() +
                " ATK: " + getAtaque() +
                " DEF: " + getDefensa() +
                " SPD: " + getVelocidad();
    }

    /**
     * Implementación obligatoria del método abstracto 'mover' de {@link Personaje}.
     * Este método no se implementa ya que el movimiento se gestiona mediante IA.
     *
     * @param mapa      El mapa donde se mueve el personaje.
     * @param direccion Dirección del movimiento (no utilizada aquí).
     * @throws UnsupportedOperationException Siempre que se invoque.
     */
    @Override
    public void mover(Mapa mapa, Direccion direccion) {
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }
}