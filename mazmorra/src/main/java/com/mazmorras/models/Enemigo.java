package com.mazmorras.models;

import com.mazmorras.enums.Direccion;
import com.mazmorras.enums.TipoEnemigo;

//La clase Enemigo extiende de Personaje, lo que significa que hereda sus atributos y métodos.
//el tipo eepresenta el tipo de enemigo, definido por la enumeración TipoEnemigo.
// y la percepcion define el rango en el que el enemigo puede detectar a un objetivo.

public class Enemigo extends Personaje {
    private TipoEnemigo tipo;

    private int percepcion;

//El constructor inicializa los atributos del enemigo.
//Llama al constructor de la clase padre (Personaje) para inicializar los atributos heredados.
//Asigna valores específicos para percepcion y tipo.
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
    }

    public int getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

//Controla el movimiento del enemigo en el mapa.
//Si el objetivo está en rango, usa el método encontrarCamino del mapa para calcular la posición hacia el objetivo.
//Verifica que la nueva posición sea válida y dentro de los límites del mapa.
//Actualiza las coordenadas del enemigo (setX y setY).
//Si el objetivo no está en rango, genera movimientos aleatorios válidos dentro del mapa.
//selecciona uno al azar y actualiza las coordenadas del enemigo.
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
                }
            }
            if (!posibles.isEmpty()) {
                int[] elegido = posibles.get((int) (Math.random() * posibles.size()));
                setX(elegido[0]);
                setY(elegido[1]);
            }
        }
    }

//Método específico del enemigo que imprime un mensaje indicando que el enemigo está gruñendo.
    public void gritar() {
        System.out.println(nombre + " gruñe amenazadoramente!");
    }

    @Override
    public String toString() {
        return "Enemigo: " + getNombre() +
                " (Nivel " + getNivel() + ")" +
                " - Vida: " + getVidaActual() + "/" + getVidaMaxima() +
                " ATK: " + getAtaque() +
                " DEF: " + getDefensa() +
                " SPD: " + getVelocidad();
    }

    @Override
    public void mover(Mapa mapa, Direccion direccion) {
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }
}