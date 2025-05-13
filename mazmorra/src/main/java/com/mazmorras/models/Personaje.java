package com.mazmorras.models;

import java.util.ArrayList;
import java.util.List;

import com.mazmorras.enums.Direccion;
import com.mazmorras.interfaces.PersonajeObserver;

/**
 * Clase abstracta que representa un personaje genérico en el juego.
 * Define atributos comunes y comportamiento base como ataque, defensa, movimiento y progresión de nivel.
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
    private List<PersonajeObserver> observers = new ArrayList<>();


    /**
     * Constructor base para inicializar los atributos del personaje.
     * 
     * @param nombre      Nombre del personaje.
     * @param x           Posición X en el mapa.
     * @param y           Posición Y en el mapa.
     * @param vidaMaxima  Vida máxima del personaje.
     * @param ataque      Valor de ataque del personaje.
     * @param defensa     Valor de defensa del personaje.
     * @param velocidad   Velocidad del personaje.
     * @param nivel       Nivel inicial del personaje.
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

    public void addObserver(PersonajeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PersonajeObserver observer) {
        observers.remove(observer);
    }

    public void notifyPersonajeActualizado() {
        for (PersonajeObserver observer : observers) {
            observer.onPersonajeActualizado(this);
        }
    }


    /**
     * Método abstracto que deben implementar las subclases para definir cómo se
     * mueve el personaje en el mapa.
     * 
     * @param mapa      Mapa en el que se encuentra el personaje.
     * @param direccion Dirección en la que se desea mover.
     */
    public abstract void mover(Mapa mapa, Direccion direccion);

    /**
     * Realiza un ataque sobre otro personaje.
     * 
     * @param objetivo Personaje al que se ataca.
     * @return true si el objetivo ha sido derrotado, false en caso contrario.
     */
    public boolean atacar(Personaje objetivo) {
        int daño = calcularDaño(objetivo); // Calcula el daño infligido
        objetivo.recibirDaño(daño); // Aplica el daño al objetivo
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de estado
        return objetivo.estaDerrotado(); // Devuelve si el objetivo ha sido derrotado

    }

    /**
     * Calcula el daño infligido a un personaje objetivo.
     * Aplica una fórmula de ataque y defensa con variación aleatoria.
     * 
     * @param objetivo Personaje objetivo.
     * @return Cantidad de daño infligido.
     */
    public int calcularDaño(Personaje objetivo) {
        // Fórmula básica de daño: ataque menos la mitad de la defensa del objetivo
        int dañoBase = Math.max(1, this.ataque - objetivo.getDefensa() / 2);

        // Variación aleatoria del daño entre 80% y 120%
        double variacion = 0.8 + Math.random() * 0.4;

        return (int) Math.round(dañoBase * variacion);
    }

    /**
     * Aplica daño al personaje, reduciendo su vida actual.
     * 
     * @param cantidad Cantidad de daño recibido.
     */
    public void recibirDaño(int cantidad) {
        this.vidaActual = Math.max(0, this.vidaActual - cantidad); // La vida no puede ser negativa
        notifyPersonajeActualizado();
    }

    /**
     * Restaura vida al personaje hasta su máximo permitido.
     * 
     * @param cantidad Cantidad de vida a recuperar.
     */
    public void curar(int cantidad) {
        this.vidaActual = Math.min(vidaMaxima, this.vidaActual + cantidad); // La vida no puede exceder el máximo
        notifyPersonajeActualizado();
    }

    /**
     * Incrementa el nivel del personaje y mejora sus estadísticas base.
     */
    public void subirNivel() {
        this.nivel++; // Incrementa el nivel
        this.vidaMaxima += 5; // Aumenta la vida máxima
        this.vidaActual = vidaMaxima; // Restaura la vida al máximo
        this.ataque += 2; // Incrementa el ataque
        this.defensa += 1; // Incrementa la defensa
        this.velocidad += 1; // Incrementa la velocidad
        // Notifica a los observadores del cambio de nivel
        notifyPersonajeActualizado();
        System.out.println("¡" + this.nombre + " ha subido al nivel " + this.nivel + "!");
    }

    /**
     * Incrementa la experiencia del personaje y sube de nivel si alcanza el umbral.
     * 
     * @param cantidad Cantidad de experiencia ganada.
     */
    public void ganarExperiencia(int cantidad) {
        this.experiencia += cantidad; // Incrementa la experiencia
        // Si la experiencia acumulada supera el umbral, sube de nivel
        if (this.experiencia >= this.nivel * 100) {
            subirNivel(); // Sube de nivel
            this.experiencia = 0; // Reinicia la experiencia
        }
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de experiencia
    }

    /**
     * Verifica si el personaje ha sido derrotado (vida actual menor o igual a cero).
     * 
     * @return true si está derrotado, false en caso contrario.
     */
    public boolean estaDerrotado() {
        return vidaActual <= 0; // Derrotado si la vida actual es 0 o menos
    }

    /**
     * Verifica si otro personaje está dentro de un rango de percepción.
     * 
     * @param otro  Otro personaje a evaluar.
     * @param rango Rango de percepción (en casillas).
     * @return true si el otro personaje está dentro del rango.
     */
    public boolean estaEnRango(Personaje otro, int rango) {
        int dx = Math.abs(this.x - otro.x); // Distancia en X
        int dy = Math.abs(this.y - otro.y); // Distancia en Y
        return dx <= rango && dy <= rango; // Devuelve true si está dentro del rango
    }

    /**
     * @return Nombre del personaje.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return Posición X del personaje.
     */
    public int getX() {
        return x;
    }

    /**
     * @return Posición Y del personaje.
     */
    public int getY() {
        return y;
    }

    /**
     * @param x Nueva posición X del personaje.
     */
    public void setX(int x) {
        this.x = x;
    notifyPersonajeActualizado(); // Notifica a los observadores del cambio de posición
    }
    
    /**
     * @param y Nueva posición Y del personaje.
     */
    public void setY(int y) {
        this.y = y;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de posición
    }

    /**
     * @return Vida actual del personaje.
     */
    public int getVidaActual() {
        return vidaActual;
    }

    /**
     * @return Vida máxima del personaje.
     */
    public int getVidaMaxima() {
        return vidaMaxima;
    }

    /**
     * @return Valor de ataque del personaje.
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * @return Valor de defensa del personaje.
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * @return Velocidad del personaje.
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * @param vidaMaxima Nueva vida máxima del personaje.
     */
    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de vida máxima
    }

    /**
     * @param ataque Nuevo valor de ataque del personaje.
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de ataque
    }

    /**
     * @param defensa Nuevo valor de defensa del personaje.
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de defensa
    }

    /**
     * @param velocidad Nueva velocidad del personaje.
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de velocidad
    }

    /**
     * @param experiencia Nueva experiencia acumulada del personaje.
     */
    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de experiencia
    }

    /**
     * @param nivel Nuevo nivel del personaje.
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
        notifyPersonajeActualizado(); // Notifica a los observadores del cambio de nivel
    }

    /**
     * @return Experiencia acumulada del personaje.
     */
    public int getExperiencia() {
        return experiencia;
    }

    /**
     * @return Nivel actual del personaje.
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Representación en texto del personaje con estadísticas clave.
     * 
     * @return Cadena con nombre, nivel, vida, ataque, defensa y velocidad.
     */
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