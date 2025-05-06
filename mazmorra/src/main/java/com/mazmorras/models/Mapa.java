package com.mazmorras.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mazmorras.enums.TipoObstaculo;

public class Mapa {
    private int ancho;
    private int alto;
    private List<Enemigo> enemigos;
    private List<Obstaculo> obstaculos;
    private List<Camino> caminos;
    private Entrada entrada;
    private Salida salida;
    private Heroe heroe;

    public Mapa() {
        this.obstaculos = new ArrayList<>(); // Inicializar la lista de obstáculos
        this.caminos = new ArrayList<>();    // Inicializar la lista de caminos
        this.enemigos = new ArrayList<>(); // Inicializar la lista de enemigos
    }
    
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
    public void setAlto(int alto) {
        this.alto = alto;
    }

    public List<Obstaculo> getObstaculos() {
        return obstaculos;
    }

    public List<Camino> getCaminos() {
        return caminos;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEnemigos(List<Enemigo> enemigos) {
        this.enemigos = enemigos;   
    }

    public Salida getSalida() {
        return salida;
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
        return ancho; 
    }

    public int getAlto() {
        return alto; 
    }

    public boolean esObstaculo(int x, int y) {
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getX() == x && obstaculo.getY() == y) {
                return true; // Es un obstáculo
            }
        }
        return false; // No es un obstáculo
    }

    public void colocarEnemigo(Enemigo enemigo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'colocarEnemigo'");
    }

    public Enemigo[] getEnemigos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEnemigos'");
    }

    public void eliminarEnemigo(Enemigo enemigo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarEnemigo'");
    }

    public void colocarObstaculos(int x, int y, TipoObstaculo tipoObstaculo) {
        System.out.println("pared colocada en: " + x + ", " + y);
        obstaculos.add(new Obstaculo(x, y, tipoObstaculo));
    }



    public void colocarCamino(int x, int y) {
        System.out.println("camino colocado en: " + x + ", " + y);
        caminos.add(new Camino(x, y));
    }

    public void colocarEntrada(int x, int y) {
        System.out.println("entrada colocada en: " + x + ", " + y);
        entrada = new Entrada(x, y);
    }

    public void colocarSalida(int x, int y) {
       System.out.println("salida colocada en: " + x + ", " + y);
        salida = new Salida(x, y);
    }

    public void colocarHeroe(Heroe heroe) {
        this.heroe = heroe;
    }

    public Heroe getHeroe() {
        return heroe;
    }

    public char getContenido(int i, int j) {
        // Verifica si la celda es un obstáculo
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getX() == i && obstaculo.getY() == j) {
                return '#'; // Representa un obstáculo como '#'
            }
        }

        // Verifica si la celda es la entrada
        if (entrada != null && entrada.getX() == i && entrada.getY() == j) {
            return 'P'; // Representa la entrada como 'P'
        }

        // Verifica si la celda es la salida
        if (salida != null && salida.getX() == i && salida.getY() == j) {
            return 'S'; // Representa la salida como 'S'
        }

        // Verifica si la celda es un camino
        for (Camino camino : caminos) {
            if (camino.getX() == i && camino.getY() == j) {
                return '.'; // Representa un camino como '.'
            }
        }

        // Si no coincide con nada, devuelve un carácter vacío
        return ' ';
    }
}