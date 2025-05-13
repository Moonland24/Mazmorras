package com.mazmorras.interfaces;

import com.mazmorras.models.Mapa;
import com.mazmorras.models.Heroe;
import com.mazmorras.models.Enemigo;

/**
 * Interfaz para observar y reaccionar a los eventos principales del juego.
 * 
 * @author JuanFran
 * @author Inma
 */
public interface JuegoObserver {
    /**
     * Notifica cambios en el estado del juego.
     * @param mapa Instancia actual del mapa.
     */
    void onJuegoActualizado(Mapa mapa);

    /**
     * Notifica cuando el héroe ha sido movido.
     * @param heroe Héroe actualizado.
     */
    void onHeroeMovido(Heroe heroe);

    /**
     * Notifica cuando un enemigo ha sido movido o eliminado.
     * @param enemigo Enemigo afectado.
     * @param eliminado True si fue eliminado.
     */
    void onEnemigoActualizado(Enemigo enemigo, boolean eliminado);

    /**
     * Notifica el inicio de un combate.
     * @param heroe Héroe en combate.
     * @param enemigo Enemigo en combate.
     */
    void onCombateIniciado(Heroe heroe, Enemigo enemigo);

    /**
     * Notifica cuando el héroe está cerca de un enemigo.
     * @param heroe Héroe en combate.
     * @param enemigo Enemigo en combate.
     */
    void onCombateCercano(Heroe heroe, Enemigo enemigo);

    /**
     * Notifica el fin del juego.
     * @param victoria True si el héroe ganó.
     */
    void onGameOver(boolean victoria);
}