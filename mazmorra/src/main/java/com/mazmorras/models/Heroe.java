package com.mazmorras.models;

import com.mazmorras.enums.Direccion;

/**
 * Representa al héroe principal controlado por el jugador.
 * Hereda de la clase abstracta {@link Personaje} y sobrescribe
 * su comportamiento de movimiento para adaptarse a la entrada del usuario.
 * 
 * Este personaje puede desplazarse por el mapa en las cuatro direcciones cardinales,
 * siempre que el movimiento sea válido.
 * 
 * @author Inma
 * @author JuanFran
 */
public class Heroe extends Personaje {

    /**
     * Crea una nueva instancia del héroe con los atributos especificados.
     *
     * @param nombre     Nombre del héroe.
     * @param x          Coordenada X inicial en el mapa.
     * @param y          Coordenada Y inicial en el mapa.
     * @param vidaMaxima Vida máxima del héroe.
     * @param ataque     Valor de ataque.
     * @param defensa    Valor de defensa.
     * @param velocidad  Velocidad de movimiento.
     * @param nivel      Nivel inicial del héroe.
     */
    public Heroe(String nombre, int x, int y, int vidaMaxima, int ataque, int defensa, int velocidad, int nivel) {
        // Llamada al constructor de la clase base (Personaje) para inicializar los
        // atributos comunes
        super(nombre, x, y, vidaMaxima, ataque, defensa, velocidad, nivel);
    }

    /**
     * Mueve al héroe en el mapa según la dirección especificada.
     * 
     * Valida que el movimiento no lleve al héroe fuera de los límites del mapa
     * ni a una celda ocupada por un obstáculo. Este método es invocado por la lógica
     * de control del usuario (teclas de dirección o WASD).
     *
     * @param mapa      Instancia del mapa donde se encuentra el héroe.
     * @param direccion Dirección del movimiento solicitado.
     * @throws IllegalArgumentException Si el mapa o la dirección son nulos.
     * @throws IllegalStateException Si el movimiento es inválido.
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
     * Devuelve una representación en texto del héroe.
     * Incluye nombre, nivel, vida actual y máxima, ataque, defensa y velocidad.
     *
     * @return Cadena con los datos principales del héroe.
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