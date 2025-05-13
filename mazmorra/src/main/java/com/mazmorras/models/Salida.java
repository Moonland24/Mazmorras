package com.mazmorras.models;

/**
 * La clase Salida representa una celda de salida en una mazmorra o laberinto.
 * Extiende la clase Celda, heredando sus propiedades y comportamiento.
 * Esta clase se utiliza para definir el punto de salida en el laberinto, identificado por sus coordenadas (x, y).
 * 
 * @author JuanFran
 * @author Inma
 */
public class Salida extends Celda {
    
    public Salida (int x, int y) {
        super(x, y);
    }
}
