package com.mazmorras.models;

import java.util.Arrays;

public enum TipoEnemigo {
    // Tipos básicos de enemigos
    GOBLIN("Goblin", 15, 8, 3, 6, 4, 1),
    ORCO("Orco", 25, 12, 5, 4, 3, 2),
    ESQUELETO("Esqueleto", 20, 10, 4, 5, 5, 1),
    ARANHA("Araña", 12, 6, 2, 7, 6, 1),
    MAGO_OSCURO("Mago Oscuro", 18, 15, 2, 5, 7, 3),
    TROLL("Troll", 40, 14, 6, 3, 2, 3);

    // Atributos de cada tipo de enemigo
    private final String nombre;
    private final int vidaBase;
    private final int ataqueBase;
    private final int defensaBase;
    private final int velocidadBase;
    private final int percepcionBase;
    private final int nivelBase;

    // Constructor
    TipoEnemigo(String nombre, int vidaBase, int ataqueBase, int defensaBase,
            int velocidadBase, int percepcionBase, int nivelBase) {
        this.nombre = nombre;
        this.vidaBase = vidaBase;
        this.ataqueBase = ataqueBase;
        this.defensaBase = defensaBase;
        this.velocidadBase = velocidadBase;
        this.percepcionBase = percepcionBase;
        this.nivelBase = nivelBase;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getVidaBase() {
        return vidaBase;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public int getDefensaBase() {
        return defensaBase;
    }

    public int getVelocidadBase() {
        return velocidadBase;
    }

    public int getPercepcionBase() {
        return percepcionBase;
    }

    public int getNivelBase() {
        return nivelBase;
    }

    // Método para obtener un tipo aleatorio
    public static TipoEnemigo getRandom() {
        TipoEnemigo[] tipos = values();
        return tipos[(int) (Math.random() * tipos.length)];
    }

    // Método para obtener tipos por nivel de dificultad
    public static TipoEnemigo getPorDificultad(int nivelDificultad) {
        TipoEnemigo[] adecuados = Arrays.stream(values())
                .filter(t -> t.nivelBase <= nivelDificultad)
                .toArray(TipoEnemigo[]::new);

        if (adecuados.length == 0)
            return GOBLIN; // Por defecto

        return adecuados[(int) (Math.random() * adecuados.length)];
    }
}