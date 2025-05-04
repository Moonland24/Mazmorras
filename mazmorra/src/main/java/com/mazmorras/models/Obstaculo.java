package com.mazmorras.models;

import com.mazmorras.enums.TipoObstaculo;

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
