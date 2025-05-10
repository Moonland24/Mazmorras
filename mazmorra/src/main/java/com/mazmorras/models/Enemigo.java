package com.mazmorras.models;

import com.mazmorras.enums.Direccion;
import com.mazmorras.enums.TipoEnemigo;

public class Enemigo extends Personaje {
    private TipoEnemigo tipo;

    private int percepcion;

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

    public void moverEnemigo(Mapa mapa, Personaje objetivo) {
        // IA simple: perseguir al jugador si está en rango
        if (estaEnRango(objetivo, this.percepcion)) {
            int[] nuevaPos = mapa.encontrarCamino(x, y, objetivo.getX(), objetivo.getY());
            if (nuevaPos != null && mapa.isValidMove(nuevaPos[0], nuevaPos[1])) {
                setX(nuevaPos[0]);
                setY(nuevaPos[1]);
            }
        } else {
            // Movimiento aleatorio SOLO si es válido
            int[][] movimientos = { {0,1}, {1,0}, {0,-1}, {-1,0} };
            java.util.List<int[]> posibles = new java.util.ArrayList<>();
            for (int[] mov : movimientos) {
                int nx = x + mov[0];
                int ny = y + mov[1];
                if (mapa.isValidMove(nx, ny)) {
                    posibles.add(new int[]{nx, ny});
                }
            }
            if (!posibles.isEmpty()) {
                int[] elegido = posibles.get((int)(Math.random() * posibles.size()));
                setX(elegido[0]);
                setY(elegido[1]);
            }
        }
    }

    // Puedes añadir métodos específicos para enemigos
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }
}