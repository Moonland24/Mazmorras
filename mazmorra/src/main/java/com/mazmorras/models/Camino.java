package com.mazmorras.models;

//La clase Camino representa una celda de camino en una mazmorra o laberinto.
//Extiende la clase Celda, heredando sus propiedades y comportamientos, se utiliza para definir un tipo específico de celda que puede
//ser atravesada dentro de la estructura del laberinto o mazmorra.
public class Camino extends Celda {
    public Camino (int x, int y){
        super(x, y);
    }
}
