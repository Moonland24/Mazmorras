package com.mazmorras.models;

public class Enemigo extends Personaje {
    private TipoEnemigo tipo;
    private int percepcion;

    public Enemigo(String nombre, int x, int y, TipoEnemigo tipo) {
        super(nombre, x, y,
                tipo.getVidaBase(),
                tipo.getAtaqueBase(),
                tipo.getDefensaBase(),
                tipo.getVelocidadBase(),
                tipo.getPercepcionBase());
        this.tipo = tipo;
    }

    @Override
    public void mover(Mapa mapa, Personaje objetivo) {
        // IA simple: perseguir al jugador si está en rango
        if (estaEnRango(objetivo, this.percepcion)) {
            int[] nuevaPos = mapa.encontrarCamino(x, y, objetivo.getX(), objetivo.getY());
            setX(nuevaPos[0]);
            setY(nuevaPos[1]);
        } else {
            // Movimiento aleatorio
            int[] movimientos = { -1, 0, 1 };
            int dx = movimientos[(int) (Math.random() * 3)];
            int dy = movimientos[(int) (Math.random() * 3)];

            if (mapa.isValidMove(x + dx, y + dy)) {
                setX(x + dx);
                setY(y + dy);
            }
        }
    }

    // Puedes añadir métodos específicos para enemigos
    public void gritar() {
        System.out.println(nombre + " gruñe amenazadoramente!");
    }
}