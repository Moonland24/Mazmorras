package com.mazmorras.models;

import com.mazmorras.enums.TipoObstaculo;


///La clase Obstaculo representa un obstáculo en la mazmorra, que es un tipo de celda (Celda).
//Contiene información sobre el tipo de obstáculo y su posición.
//Esta clase extiende la clase Celda y añade funcionalidad específica para los obstáculos.
public class Obstaculo extends Celda {

    private TipoObstaculo tipoObstaculo;
    public Obstaculo (int x, int y, TipoObstaculo tipoObstaculo){
        super(x, y);

        this.tipoObstaculo = tipoObstaculo;
    }
    public TipoObstaculo getTipoObstaculo() {
        return tipoObstaculo;
    }
    public void setTipoObstaculo(TipoObstaculo tipoObstaculo) {
        this.tipoObstaculo = tipoObstaculo;
    }
}