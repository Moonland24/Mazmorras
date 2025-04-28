package com.mazmorras.controllers;

import com.mazmorras.models.Heroe;
import com.mazmorras.views.CreacionPersonajeView;

public class CreacionPersonajeController {
    private CreacionPersonajeView view;

    public CreacionPersonajeController(CreacionPersonajeView view) {
        this.view = view;
    }

    /**
     * Método para crear un nuevo héroe con los datos proporcionados.
     * 
     * @param nombre     Nombre del héroe.
     * @param x          Posición X inicial.
     * @param y          Posición Y inicial.
     * @param vidaMaxima Vida máxima del héroe.
     * @param ataque     Valor de ataque.
     * @param defensa    Valor de defensa.
     * @param velocidad  Velocidad del héroe.
     * @param nivel      Nivel inicial del héroe.
     * @return Heroe creado.
     */
    public Heroe crearHeroe(String nombre, int x, int y, int vidaMaxima, int ataque, int defensa, int velocidad, int nivel) {
        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new IllegalArgumentException("Nombre no válido.");
            }
            if (vidaMaxima <= 0 || ataque <= 0 || defensa <= 0 || velocidad <= 0 || nivel <= 0) {
                throw new IllegalArgumentException("Stats deben ser > 0.");
            }
            Heroe heroe = new Heroe(nombre, x, y, vidaMaxima, ataque, defensa, velocidad, nivel);
            mostrarHeroeCreado(heroe);
            return heroe;
        } catch (IllegalArgumentException e) {
            if (view != null) {
                view.mostrarError(e.getMessage()); // Notifica a la vista
            }
            return null;
        }
    }
    /**
     * Método para mostrar los detalles del héroe creado en la vista.
     * 
     * @param heroe Héroe creado.
     */
    private void mostrarHeroeCreado(Heroe heroe) {
        if (view != null) {
            view.mostrarDetallesHeroe(heroe);
        }
    }
}
