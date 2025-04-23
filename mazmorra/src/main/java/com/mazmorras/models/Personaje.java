package com.mazmorras.models;

public abstract class Personaje {
    // Atributos básicos
    protected String nombre;
    protected int x; // Posición en el mapa
    protected int y;
    private int vidaMaxima;
    private int vidaActual;
    private int ataque;
    private int defensa;
    private int velocidad;
    protected int percepcion; // Rango de detección para enemigos
    private int experiencia;
    private int nivel;

    // Constructor base
    public Personaje(String nombre, int x, int y, int vidaMaxima, int ataque,
            int defensa, int velocidad, int percepcion) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.percepcion = percepcion;
        this.experiencia = 0;
        this.nivel = 1;
    }

    // Métodos abstractos que deben implementar las subclases
    public abstract void mover(Mapa mapa, Personaje objetivo);

    // Método para realizar un ataque
    public boolean atacar(Personaje objetivo) {
        int daño = calcularDaño(objetivo);
        objetivo.recibirDaño(daño);
        return objetivo.estaDerrotado();
    }

    // Cálculo de daño (puede sobrescribirse en subclases)
    protected int calcularDaño(Personaje objetivo) {
        // Fórmula básica de daño
        int dañoBase = Math.max(1, this.ataque - objetivo.getDefensa() / 2);

        // Variabilidad de daño (80%-120%)
        double variacion = 0.8 + Math.random() * 0.4;
        return (int) Math.round(dañoBase * variacion);
    }

    // Método para recibir daño
    public void recibirDaño(int cantidad) {
        this.vidaActual = Math.max(0, this.vidaActual - cantidad);
    }

    // Método para curar
    public void curar(int cantidad) {
        this.vidaActual = Math.min(vidaMaxima, this.vidaActual + cantidad);
    }

    // Método para subir de nivel
    public void subirNivel() {
        this.nivel++;
        this.vidaMaxima += 5;
        this.vidaActual = vidaMaxima; // Restaurar vida al subir de nivel
        this.ataque += 2;
        this.defensa += 1;
        this.velocidad += 1;
    }

    // Método para ganar experiencia
    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad;
        // Cada 100 puntos de experiencia sube de nivel
        if (this.experiencia >= this.nivel * 100) {
            subirNivel();
            this.experiencia = 0;
        }
    }

    // Métodos de estado
    public boolean estaDerrotado() {
        return vidaActual <= 0;
    }

    public boolean estaEnRango(Personaje otro, int rango) {
        int dx = Math.abs(this.x - otro.x);
        int dy = Math.abs(this.y - otro.y);
        return dx <= rango && dy <= rango;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
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

    public int getVidaActual() {
        return vidaActual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getPercepcion() {
        return percepcion;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    // Representación textual del personaje
    @Override
    public String toString() {
        return String.format("%s (Nivel %d) - Vida: %d/%d ATK: %d DEF: %d SPD: %d",
                nombre, nivel, vidaActual, vidaMaxima, ataque, defensa, velocidad);
    }
}