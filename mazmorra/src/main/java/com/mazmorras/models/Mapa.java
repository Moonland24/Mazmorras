package com.mazmorras.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private int ancho;
    private int alto;
    private List<Enemigo> enemigos;
    private Heroe heroe;

    public Mapa(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public int[] encontrarCamino(int x, int y, int x2, int y2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'encontrarCamino'");
    }

    public boolean isValidMove(int i, int j) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }

    public int getAncho() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAncho'");
    }

    public int getAlto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlto'");
    }

    public boolean esObstaculo(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'esObstaculo'");
    }

    public void colocarEnemigo(Enemigo enemigo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'colocarEnemigo'");
    }
  
}