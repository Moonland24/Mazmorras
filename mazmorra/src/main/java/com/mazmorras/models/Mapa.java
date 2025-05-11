package com.mazmorras.models;


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
        this.caminos = new ArrayList<>(); // Inicializar la lista de caminos
        this.enemigos = new ArrayList<>(); // Inicializar la lista de enemigos
        this.heroe = null; // Inicializar el héroe
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

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public Heroe getHeroe() {
        return heroe;
    }

    public void setHeroe(Heroe heroe) {
        this.heroe = heroe;
    }
    public List<Enemigo> getEnemigos() {
        return enemigos;
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

    public int[] encontrarCamino(int x, int y, int x2, int y2) {
        boolean[][] visited = new boolean[alto][ancho];
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        List<int[]> queue = new ArrayList<>();
        queue.add(new int[] { x, y });
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.remove(0);
            int cx = current[0], cy = current[1];

            if (cx == x2 && cy == y2) {
                return new int[] { cx, cy };
            }

            for (int[] dir : directions) {
                int nx = cx + dir[0], ny = cy + dir[1];
                if (nx >= 0 && ny >= 0 && nx < ancho && ny < alto && !visited[ny][nx] && !esObstaculo(nx, ny)) {
                    queue.add(new int[] { nx, ny });
                    visited[ny][nx] = true;
                }
            }
        }

        return null;
    }

    public boolean isValidMove(int i, int j) {

        if (getObstaculos().stream().anyMatch(obstaculo -> obstaculo.getX() == i && obstaculo.getY() == j)) {
            return false;
        } else if (getCaminos().stream().anyMatch(camino -> camino.getX() == i && camino.getY() == j)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esObstaculo(int x, int y) {
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getX() == x && obstaculo.getY() == y) {
                return true;
            }
        }
        return false;
    }

    // Hay que darle una vuelta a esto
    public void colocarEnemigo(Enemigo enemigo) {
        if (enemigo != null) {
            enemigos.add(enemigo);
            System.out.println("El enemigo ha sido colocado en: " + enemigo.getY() + ", " + enemigo.getX());
        } else {
            System.out.println("El enemigo no se pudo colocar");
        }
    }

    public void eliminarEnemigo(Enemigo enemigo) {
        if (enemigo != null && enemigos.contains(enemigo)) {
            enemigos.remove(enemigo);
            System.out
                    .println("El enemigo ha sido eliminado de su posición: " + enemigo.getX() + ", " + enemigo.getY());
        } else {
            System.out.println("El enemigo no se encontró asi que pues no se pudo eliminar.");
        }
    }

    // Esto es una redundancia muy gorda -- Hay que refactorizar porque esto es
    // bastante grave
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
