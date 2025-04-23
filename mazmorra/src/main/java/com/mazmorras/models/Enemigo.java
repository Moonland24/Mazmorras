package com.mazmorras.models;

public class Enemigo extends Personaje {
    public Enemigo(String nombre, int x, int y, int vidaMaxima, int ataque, int defensa, int velocidad,
            int percepcion) {
        super(nombre, x, y, vidaMaxima, ataque, defensa, velocidad, percepcion);
        //TODO Auto-generated constructor stub
    }

    protected int percepcion;// Rango de detección de heroe este atributo solo para enemigos

    @Override
    public void mover(Mapa mapa, Personaje objetivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }
}