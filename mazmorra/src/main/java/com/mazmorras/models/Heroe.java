package com.mazmorras.models;

import com.mazmorras.enums.Direccion;

/**
 * Clase que representa al héroe protagonista controlado por el jugador.
 * Extiende la clase abstracta Personaje.
 * 
 * @author Inma
 * @author JuanFran
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
    public Heroe(String nombre, int x, int y, int vidaMaxima, int ataque, int defensa, int velocidad, int nivel) {
        // Llamada al constructor de la clase base (Personaje) para inicializar los
        // atributos comunes
        super(nombre, x, y, vidaMaxima, ataque, defensa, velocidad, nivel);
    }

    /**
     * Método para mover al héroe en el mapa. El movimiento se controla mediante
     * las teclas de flecha o WASD.
     * 
     * @param mapa     Mapa en el que se encuentra el héroe.
     * @param direccion No se utiliza en el caso del héroe, pero se incluye para
     *                 cumplir con la firma del método abstracto.
     */
    @Override
    public void mover(Mapa mapa, Direccion direccion) {
        if (mapa == null || direccion == null) {
            throw new IllegalArgumentException("El mapa y la dirección no pueden ser nulos.");
        }

        // Calcular nueva posición basada en la dirección
        int nuevaX = getX();
        int nuevaY = getY();

        switch (direccion) {
            case ARRIBA:
                nuevaY--;
                break;
            case ABAJO:
                nuevaY++;
                break;
            case IZQUIERDA:
                nuevaX--;
                break;
            case DERECHA:
                nuevaX++;
                break;
            default:
                throw new IllegalArgumentException("Dirección no válida.");
        }

        // Validar que la nueva posición esté dentro de los límites del mapa
        if (nuevaX < 0 || nuevaX >= mapa.getAncho() || nuevaY < 0 || nuevaY >= mapa.getAlto()) {
            throw new IllegalStateException("El héroe no puede moverse fuera de los límites del mapa.");
        }

        // Validar que la nueva posición no esté ocupada por un obstáculo
        if (mapa.esObstaculo(nuevaX, nuevaY)) {
            throw new IllegalStateException("El héroe no puede moverse a una posición ocupada por un obstáculo.");
        }

        // Actualizar la posición del héroe
        setX(nuevaX);
        setY(nuevaY);
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