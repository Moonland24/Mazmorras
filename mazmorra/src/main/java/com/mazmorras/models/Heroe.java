package com.mazmorras.models;

import com.mazmorras.models.Mapa;

/**
 * Clase que representa al héroe protagonista controlado por el jugador.
 * Extiende la clase abstracta Personaje.
 * 
 * @author Inma
 * @author Juan
 */
public class Heroe extends Personaje {

    /**
     * Constructor para inicializar los atributos del héroe.
     * 
     * @param nombre     Nombre del héroe.
     * @param x          Posición X inicial en el mapa.
     * @param y          Posición Y inicial en el mapa.
     * @param vidaMaxima Vida máxima del héroe.
     * @param ataque     Valor de ataque del héroe.
     * @param defensa    Valor de defensa del héroe.
     * @param velocidad  Velocidad del héroe.
     */
    public Heroe(String nombre, int x, int y, int vidaMaxima, int ataque, int defensa, int velocidad) {
        // Llamada al constructor de la clase base (Personaje) para inicializar los atributos comunes
        super(nombre, x, y, vidaMaxima, ataque, defensa, velocidad);
    }

    /**
     * Método para mover al héroe en el mapa. El movimiento se controla mediante
     * las teclas de flecha o WASD.
     * 
     * @param mapa    Mapa en el que se encuentra el héroe.
     * @param objetivo No se utiliza en el caso del héroe, pero se incluye para cumplir con la firma del método abstracto.
     */
    @Override
    public void mover(Mapa mapa, Personaje objetivo) {
        // Este método será implementado en el controlador del juego para manejar
        // las entradas del usuario (teclas de flecha o WASD).
        // Validar movimientos del héroe en el mapa
        if (mapa == null) {
            throw new IllegalArgumentException("El mapa no puede ser nulo.");
        }

        // Obtener las dimensiones del mapa
        int anchoMapa = mapa.getAncho();
        int altoMapa = mapa.getAlto();

        // Validar que el héroe no salga de los límites del mapa
        if (getX() < 0 || getX() >= anchoMapa || getY() < 0 || getY() >= altoMapa) {
            throw new IllegalStateException("El héroe está fuera de los límites del mapa.");
        }

        // Validar que la posición no esté ocupada por un obstáculo
        if (mapa.esObstaculo(getX(), getY())) {
            throw new IllegalStateException("El héroe no puede moverse a una posición ocupada por un obstáculo.");
        }
    }

    /**
     * Método para representar al héroe como una cadena de texto.
     * Incluye información adicional específica del héroe.
     */
    @Override
    public String toString() {
        return "Héroe: " + getNombre() +
               " (Nivel " + getNivel() + ")" +
               " - Vida: " + getVidaActual() + "/" + getVidaMaxima() +
               " ATK: " + getAtaque() +
               " DEF: " + getDefensa() +
               " SPD: " + getVelocidad();
    }
}