package com.mazmorras.interfaces;

import com.mazmorras.models.Mapa;
import com.mazmorras.models.Heroe;
import com.mazmorras.models.Enemigo;

public interface JuegoObserver {
    /**
     * Notifica cambios en el estado del juego.
     * @param juego Instancia actual del juego.
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
     * comprobar si el heroe esta cerca del enemigo
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