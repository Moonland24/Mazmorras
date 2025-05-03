package com.mazmorras.models;

public class Obstaculo {
    private int x;
    private int y;
    //Puedes poner el tipo de obstaculo sea pared, barril, etc

    public Obstaculo(int x, int y) {
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
