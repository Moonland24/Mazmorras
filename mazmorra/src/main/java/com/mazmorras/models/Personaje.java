package com.mazmorras.models;

/**
 * Clase abstracta que representa un personaje genérico en el juego.
 * 
 * @author Inma
 * @author Juanfran
 */
public abstract class Personaje {
    // Atributos básicos del personaje
    protected String nombre; // Nombre del personaje
    protected int x; // Posición X en el mapa
    protected int y; // Posición Y en el mapa
    private int vidaMaxima; // Vida máxima del personaje
    private int vidaActual; // Vida actual del personaje
    private int ataque; // Valor de ataque del personaje
    private int defensa; // Valor de defensa del personaje
    private int velocidad; // Velocidad del personaje
    private int experiencia; // Experiencia acumulada
    private int nivel; // Nivel actual del personaje

    /**
     * Constructor base para inicializar los atributos del personaje.
     * 
     * @param nombre     Nombre del personaje.
     * @param x          Posición X en el mapa.
     * @param y          Posición Y en el mapa.
     * @param vidaMaxima Vida máxima del personaje.
     * @param vidaActual Vida actual del personaje.
     * @param ataque     Valor de ataque del personaje.
     * @param defensa    Valor de defensa del personaje.
     * @param velocidad  Velocidad del personaje.
     * @param experiencia Experiencia acumulada.
     * @param nivel      Nivel actual del personaje.
     */
    public Personaje(String nombre, int x, int y, int vidaMaxima, int ataque,
            int defensa, int velocidad, int nivel) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima; // Vida actual comienza al máximo
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.experiencia = 0; // Experiencia inicial
        this.nivel = nivel; // Nivel inicial
    }


    /**
     * Método abstracto que deben implementar las subclases para definir cómo se
     * mueve el personaje.
     * 
     * @param mapa     Mapa en el que se encuentra el personaje.
     * @param objetivo Personaje objetivo hacia el que se mueve.
     */
    public abstract void mover(Mapa mapa, Personaje objetivo);

    // Método para realizar un ataque a otro personaje
    public boolean atacar(Personaje objetivo) {
        int daño = calcularDaño(objetivo); // Calcula el daño infligido
        objetivo.recibirDaño(daño); // Aplica el daño al objetivo
        return objetivo.estaDerrotado(); // Devuelve si el objetivo ha sido derrotado
    }

    // Método para calcular el daño infligido a un objetivo
    public int calcularDaño(Personaje objetivo) {
        // Fórmula básica de daño: ataque menos la mitad de la defensa del objetivo
        int dañoBase = Math.max(1, this.ataque - objetivo.getDefensa() / 2);

        // Variación aleatoria del daño entre 80% y 120%
        double variacion = 0.8 + Math.random() * 0.4;
        return (int) Math.round(dañoBase * variacion);
    }

    // Método para recibir daño y reducir la vida actual
    public void recibirDaño(int cantidad) {
        this.vidaActual = Math.max(0, this.vidaActual - cantidad); // La vida no puede ser negativa
    }

    // Método para curar al personaje
    public void curar(int cantidad) {
        this.vidaActual = Math.min(vidaMaxima, this.vidaActual + cantidad); // La vida no puede exceder el máximo
    }

    // Método para subir de nivel al personaje
    public void subirNivel() {
        this.nivel++; // Incrementa el nivel
        this.vidaMaxima += 5; // Aumenta la vida máxima
        this.vidaActual = vidaMaxima; // Restaura la vida al máximo
        this.ataque += 2; // Incrementa el ataque
        this.defensa += 1; // Incrementa la defensa
        this.velocidad += 1; // Incrementa la velocidad
    }

    // Método para ganar experiencia
    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad; // Incrementa la experiencia
        // Si la experiencia acumulada supera el umbral, sube de nivel
        if (this.experiencia >= this.nivel * 100) {
            subirNivel(); // Sube de nivel
            this.experiencia = 0; // Reinicia la experiencia
        }
    }

    // Método para verificar si el personaje ha sido derrotado
    public boolean estaDerrotado() {
        return vidaActual <= 0; // Derrotado si la vida actual es 0 o menos
    }

    // Método para verificar si otro personaje está dentro del rango de percepción
    public boolean estaEnRango(Personaje otro, int rango) {
        int dx = Math.abs(this.x - otro.x); // Distancia en X
        int dy = Math.abs(this.y - otro.y); // Distancia en Y
        return dx <= rango && dy <= rango; // Devuelve true si está dentro del rango
    }

    // Métodos getter y setter para acceder y modificar los atributos del personaje
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

    public int getExperiencia() {
        return experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    // Método para representar al personaje como una cadena de texto
    @Override
    public String toString() {
        return "Personaje: " + getNombre() +
                " (Nivel " + getNivel() + ")" +
                " - Vida: " + getVidaActual() + "/" + getVidaMaxima() +
                " ATK: " + getAtaque() +
                " DEF: " + getDefensa() +
                " SPD: " + getVelocidad();
    }
}