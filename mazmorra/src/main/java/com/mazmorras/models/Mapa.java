package com.mazmorras.models;

import java.util.ArrayList;
import java.util.List;

import com.mazmorras.enums.TipoObstaculo;

public class Mapa {
    //Atributos del mapa
    private int ancho;                      // Ancho del mapa
    private int alto;                       // Alto del mapa
    private List<Enemigo> enemigos;         // Lista de enemigos en el mapa
    private List<Obstaculo> obstaculos;     // Lista de obstáculos en el mapa
    private List<Camino> caminos;           // Lista de caminos en el mapa
    private Entrada entrada;                // Entrada del mapa
    private Salida salida;                  // Salida del mapa
    private Heroe heroe;                    // Héroe en el mapa

    // Constructor de la clase Mapa
    public Mapa() {
        this.obstaculos = new ArrayList<>();        // Inicializa la lista de obstáculos
        this.caminos = new ArrayList<>();           // Inicializa la lista de caminos
        this.enemigos = new ArrayList<>();          // Inicializa la lista de enemigos
        this.heroe = null;                          // Inicializa el héroe
    }

    //Métodos para establecer el ancho del mapa
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    //Métodos para establecer el alto del mapa
    public void setAlto(int alto) {
        this.alto = alto;
    }

    //Obtiene la lista de obstáculos
    public List<Obstaculo> getObstaculos() {
        return obstaculos;
    }

    //Obtiene la lista de caminos
    public List<Camino> getCaminos() {
        return caminos;
    }

    //Obtiene la entrada del mapa
    public Entrada getEntrada() {
        return entrada;
    }

    //Establece la lista de enemigos
    public void setEnemigos(List<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    //Obtiene la salida del mapa
    public Salida getSalida() {
        return salida;
    }

    //Obteiene el ancho del mapa
    public int getAncho() {
        return ancho;
    }

    //Obtiene el alto del mapa
    public int getAlto() {
        return alto;
    }

    //Obtiene el héroe del mapa
    public Heroe getHeroe() {
        return heroe;
    }

    //Establece el héroe en el mapa
    public void setHeroe(Heroe heroe) {
        this.heroe = heroe;
    }

    //Obtiene la lista de enemigos
    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    //Coloca un obstáculo en el mapa
    public void colocarObstaculos(int x, int y, TipoObstaculo tipoObstaculo) {
        System.out.println("pared colocada en: " + x + ", " + y);
        obstaculos.add(new Obstaculo(x, y, tipoObstaculo));
    }

    //Coloca un camino en el mapa
    public void colocarCamino(int x, int y) {
        System.out.println("camino colocado en: " + x + ", " + y);
        caminos.add(new Camino(x, y));
    }

    //Coloca la entrada en el mapa
    public void colocarEntrada(int x, int y) {
        System.out.println("entrada colocada en: " + x + ", " + y);
        entrada = new Entrada(x, y);
    }

    //Coloca la salida en el mapa
    public void colocarSalida(int x, int y) {
        System.out.println("salida colocada en: " + x + ", " + y);
        salida = new Salida(x, y);
    }

    //Coloca el héroe en el mapa
    public void colocarHeroe(Heroe heroe) {
        this.heroe = heroe;
    }

    //Encuentra un camino entre dos puntos
    public int[] encontrarCamino(int x, int y, int x2, int y2) {
        boolean[][] visited = new boolean[alto][ancho];                     // Matriz de celdas visitadas
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };  // Direcciones posibles
        List<int[]> queue = new ArrayList<>();                              // Cola para la búsqueda
        queue.add(new int[] { x, y });
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.remove(0);
            int cx = current[0], cy = current[1];

            if (cx == x2 && cy == y2) {
                return new int[] { cx, cy }; // Camino encontrado
            }

            for (int[] dir : directions) {
                int nx = cx + dir[0], ny = cy + dir[1];
                if (nx >= 0 && ny >= 0 && nx < ancho && ny < alto && !visited[ny][nx] && !esObstaculo(nx, ny)) {
                    queue.add(new int[] { nx, ny });
                    visited[ny][nx] = true;
                }
            }
        }

        return null; //Camino no encontrado
    }

    //Verifica si un movimiento es válido
    public boolean isValidMove(int i, int j) {
        if (getObstaculos().stream().anyMatch(obstaculo -> obstaculo.getX() == i && obstaculo.getY() == j)) {
            return false;                                                                                       //Movimiento inválido si hay un obstáculo
        } else if (getCaminos().stream().anyMatch(camino -> camino.getX() == i && camino.getY() == j)) {
            return true;                                                                                        //Movimiento válido si hay un camino
        } else {
            return false;                                                                                       //Movimiento inválido en otros casos
        }
    }

    //Verifica si una celda es un obstáculo
    public boolean esObstaculo(int x, int y) {
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getX() == x && obstaculo.getY() == y) {
                return true;                                        // Es un obstáculo
            }
        }
        return false;                                              // No es un obstáculo
    }

    //Coloca un enemigo en el mapa
    public void colocarEnemigo(Enemigo enemigo) {
        if (enemigo != null) {
            enemigos.add(enemigo);
            System.out.println("El enemigo ha sido colocado en: " + enemigo.getY() + ", " + enemigo.getX());
        } else {
            System.out.println("El enemigo no se pudo colocar");
        }
    }

    //Elimina un enemigo del mapa
    public void eliminarEnemigo(Enemigo enemigo) {
        if (enemigo != null && enemigos.contains(enemigo)) {
            enemigos.remove(enemigo);
            System.out
                    .println("El enemigo ha sido eliminado de su posición: " + enemigo.getX() + ", " + enemigo.getY());
        } else {
            System.out.println("El enemigo no se encontró asi que pues no se pudo eliminar.");
        }
    }

    //Obtiene el contenido de una celda
    public char getContenido(int i, int j) {
        //Verifica si la celda es un obstáculo
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getX() == i && obstaculo.getY() == j) {
                return '#'; //Representa un obstáculo como '#'
            }
        }

        //Verifica si la celda es la entrada
        if (entrada != null && entrada.getX() == i && entrada.getY() == j) {
            return 'P'; // Representa la entrada como 'P'
        }

        //Verifica si la celda es la salida
        if (salida != null && salida.getX() == i && salida.getY() == j) {
            return 'S'; // Representa la salida como 'S'
        }

        //Verifica si la celda es un camino
        for (Camino camino : caminos) {
            if (camino.getX() == i && camino.getY() == j) {
                return '.'; // Representa un camino como '.'
            }
        }

        //Si no coincide con nada, devuelve un carácter vacío
        return ' ';
    }
}
