package com.mazmorras.interfaces;

import com.mazmorras.models.Enemigo;
import com.mazmorras.models.Mapa;
import java.util.List;

/**
 * Interfaz para la carga de archivos relacionados con el mapa, enemigos y estadísticas del héroe.
 * 
 * @author JuanFran
 * @author Inma
 */
public interface FileLoader {
    /**
     * Carga un mapa desde un archivo.
     * @param ruta Ruta del archivo del mapa (ej: "mapas/nivel1.txt").
     * @return Mapa cargado.
     */
    Mapa cargarMapa(String ruta);

    /**
     * Carga una lista de enemigos desde un archivo.
     * @param ruta Ruta del archivo de enemigos (ej: "enemigos/enemigos_nivel1.txt").
     * @return Lista de enemigos.
     */
    List<Enemigo> cargarEnemigos(String ruta);

    /**
     * Carga las estadísticas iniciales del héroe desde un archivo.
     * @param ruta Ruta del archivo de configuración (ej: "config/heroe_stats.txt").
     * @return Array con stats en orden: [vida, ataque, defensa, velocidad].
     */
    int[] cargarStatsHeroe(String ruta);
}