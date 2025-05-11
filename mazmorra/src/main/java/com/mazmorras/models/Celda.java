package com.mazmorras.models;

//Representa una celda en una cuadrícula o matriz con coordenadas x e y.
//Esta clase proporciona métodos para obtener y establecer las coordenadas de la celda.
public class Celda {
    private int x;
    private int y;

    public Celda(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
