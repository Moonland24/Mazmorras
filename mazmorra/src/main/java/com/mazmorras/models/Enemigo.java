package com.mazmorras.models;

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

    public void recibirDanio(int danioHeroe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recibirDanio'");
    }
}