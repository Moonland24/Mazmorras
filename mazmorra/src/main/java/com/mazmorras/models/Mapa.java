package com.mazmorras.models;

import java.util.ArrayList;
import java.util.List;

import com.mazmorras.enums.TipoObstaculo;

/**
 * Representa el mapa del juego, incluyendo dimensiones, caminos, obstáculos, enemigos,
 * la entrada, la salida y el héroe.
 * 
 * Este mapa sirve como entorno donde se desarrollan las interacciones y desplazamientos
 * de personajes y objetos del juego.
 * 
 * @author Inma
 * @author Juanfran
 */
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

    /**
     * Crea un mapa vacío sin dimensiones inicializadas, pero con listas preparadas.
     */
    public Mapa() {
        this.obstaculos = new ArrayList<>();        // Inicializa la lista de obstáculos
        this.caminos = new ArrayList<>();           // Inicializa la lista de caminos
        this.enemigos = new ArrayList<>();          // Inicializa la lista de enemigos
        this.heroe = null;                          // Inicializa el héroe
    }

    /** @param ancho Ancho del mapa en celdas. */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    /** @param alto Alto del mapa en celdas. */
    public void setAlto(int alto) {
        this.alto = alto;
    }

    /** @return Lista de obstáculos en el mapa. */
    public List<Obstaculo> getObstaculos() {
        return obstaculos;
    }

    /** @return Lista de caminos en el mapa. */
    public List<Camino> getCaminos() {
        return caminos;
    }

    /** @return Celda de entrada al mapa. */
    public Entrada getEntrada() {
        return entrada;
    }

    /** @param enemigos Lista de enemigos a establecer en el mapa. */
    public void setEnemigos(List<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    /** @return Celda de salida del mapa. */
    public Salida getSalida() {
        return salida;
    }

    /** @return Ancho del mapa. */
    public int getAncho() {
        return ancho;
    }

    /** @return Alto del mapa. */
    public int getAlto() {
        return alto;
    }

    /** @return Héroe presente en el mapa. */
    public Heroe getHeroe() {
        return heroe;
    }

    /** @param heroe Héroe a colocar en el mapa. */
    public void setHeroe(Heroe heroe) {
        this.heroe = heroe;
    }

    /** @return Lista de enemigos en el mapa. */
    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    /**
     * Añade un nuevo obstáculo en la posición indicada.
     * 
     * @param x             Coordenada X.
     * @param y             Coordenada Y.
     * @param tipoObstaculo Tipo de obstáculo a colocar.
     */
    public void colocarObstaculos(int x, int y, TipoObstaculo tipoObstaculo) {
        System.out.println("pared colocada en: " + x + ", " + y);
        obstaculos.add(new Obstaculo(x, y, tipoObstaculo));
    }

    /**
     * Añade un camino en la posición indicada.
     * 
     * @param x Coordenada X.
     * @param y Coordenada Y.
     */
    public void colocarCamino(int x, int y) {
        System.out.println("camino colocado en: " + x + ", " + y);
        caminos.add(new Camino(x, y));
    }

    /**
     * Establece la entrada del mapa.
     * 
     * @param x Coordenada X.
     * @param y Coordenada Y.
     */
    public void colocarEntrada(int x, int y) {
        System.out.println("entrada colocada en: " + x + ", " + y);
        entrada = new Entrada(x, y);
    }

    /**
     * Establece la salida del mapa.
     * 
     * @param x Coordenada X.
     * @param y Coordenada Y.
     */
    public void colocarSalida(int x, int y) {
        System.out.println("salida colocada en: " + x + ", " + y);
        salida = new Salida(x, y);
    }

    /**
     * Coloca al héroe en el mapa.
     * 
     * @param heroe Instancia del héroe.
     */
    public void colocarHeroe(Heroe heroe) {
        this.heroe = heroe;
    }

    /**
     * Intenta encontrar un camino entre dos coordenadas usando búsqueda en anchura.
     * 
     * @param x  Coordenada X inicial.
     * @param y  Coordenada Y inicial.
     * @param x2 Coordenada X destino.
     * @param y2 Coordenada Y destino.
     * @return Arreglo con las coordenadas del destino si hay camino, o {@code null}.
     */
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

    /**
     * Verifica si una celda es una posición válida para moverse.
     * 
     * @param i Coordenada X.
     * @param j Coordenada Y.
     * @return {@code true} si es camino y no es obstáculo; de lo contrario {@code false}.
     */
    public boolean isValidMove(int i, int j) {
        if (getObstaculos().stream().anyMatch(obstaculo -> obstaculo.getX() == i && obstaculo.getY() == j)) {
            return false;                                                                                       //Movimiento inválido si hay un obstáculo
        } else if (getCaminos().stream().anyMatch(camino -> camino.getX() == i && camino.getY() == j)) {
            return true;                                                                                        //Movimiento válido si hay un camino
        } else {
            return false;                                                                                       //Movimiento inválido en otros casos
        }
    }

    /**
     * Verifica si una celda específica contiene un obstáculo.
     * 
     * @param x Coordenada X.
     * @param y Coordenada Y.
     * @return {@code true} si hay obstáculo, de lo contrario {@code false}.
     */
    public boolean esObstaculo(int x, int y) {
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.getX() == x && obstaculo.getY() == y) {
                return true;                                        // Es un obstáculo
            }
        }
        return false;                                              // No es un obstáculo
    }

    /**
     * Añade un enemigo al mapa si no es nulo.
     * 
     * @param enemigo Enemigo a colocar.
     */
    public void colocarEnemigo(Enemigo enemigo) {
        if (enemigo != null) {
            enemigos.add(enemigo);
            System.out.println("El enemigo ha sido colocado en: " + enemigo.getY() + ", " + enemigo.getX());
        } else {
            System.out.println("El enemigo no se pudo colocar");
        }
    }

    /**
     * Elimina un enemigo del mapa si está presente.
     * 
     * @param enemigo Enemigo a eliminar.
     */

    public void eliminarEnemigo(Enemigo enemigo) {
        if (enemigo != null && enemigos.contains(enemigo)) {
            enemigos.remove(enemigo);
            System.out
                    .println("El enemigo ha sido eliminado de su posición: " + enemigo.getX() + ", " + enemigo.getY());
        } else {
            System.out.println("El enemigo no se encontró asi que pues no se pudo eliminar.");
        }
    }

    /**
     * Obtiene el carácter que representa el contenido de una celda en el mapa.
     * 
     * @param i Coordenada X.
     * @param j Coordenada Y.
     * @return Caracter representativo: '#' para obstáculo, 'P' para entrada, 'S' para salida, '.' para camino o espacio en blanco.
     */
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
