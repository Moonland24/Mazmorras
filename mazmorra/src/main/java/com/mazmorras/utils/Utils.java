package com.mazmorras.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mazmorras.enums.TipoEnemigo;
import com.mazmorras.enums.TipoObstaculo;
import com.mazmorras.models.Enemigo;
import com.mazmorras.models.Mapa;

/**
 * Clase de utilidades para operaciones de carga de datos desde archivos.
 * Contiene métodos para leer enemigos desde un archivo JSON y mapas desde archivos de texto.
 * @author Inma
 * @author Juanfran
 */
public class Utils {
        /**
     * Carga una lista de enemigos desde un archivo JSON.
     * El JSON debe contener un arreglo bajo la clave "enemigos", cada uno con los atributos esperados.
     *
     * @param path Ruta del archivo JSON.
     * @return Lista de enemigos construida a partir del archivo.
     * @throws IOException     Si ocurre un error al leer el archivo.
     * @throws ParseException  Si ocurre un error al parsear el contenido JSON.
     */
    public static List<Enemigo> cargarDesdeJSON(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(path);
        Object obj = parser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray array = (JSONArray) jsonObject.get("enemigos");
        List<Enemigo> auxiliar = new java.util.ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject2 = (JSONObject) array.get(i);
            // Obtener los atributos del enemigo
            String nombre = (String) jsonObject2.get("nombre");
            int vidaBase = ((Long) jsonObject2.get("vidaBase")).intValue();
            int ataqueBase = ((Long) jsonObject2.get("ataqueBase")).intValue();
            int defensaBase = ((Long) jsonObject2.get("defensaBase")).intValue();
            int velocidadBase = ((Long) jsonObject2.get("velocidadBase")).intValue();
            int percepcionBase = ((Long) jsonObject2.get("percepcionBase")).intValue();
            int nivelBase = ((Long) jsonObject2.get("nivelBase")).intValue();
            String tipo = (String) jsonObject2.get("nombre").toString().toUpperCase();
            Enemigo enemigo = new Enemigo(nombre, 0, 0, vidaBase, ataqueBase, defensaBase, velocidadBase,
                    TipoEnemigo.valueOf(tipo), percepcionBase, nivelBase);
            auxiliar.add(enemigo);
        }
        System.out.println("Enemigos cargados desde JSON:");
        for (Enemigo enemigo : auxiliar) {
            System.out.println(enemigo.getNombre() + " en (" + enemigo.getX() + ", " + enemigo.getY() + ")");
        }
        return auxiliar;
    }

        /**
     * Carga un mapa desde un archivo de texto, interpretando los caracteres para generar caminos,
     * obstáculos, entradas, salidas y ubicar enemigos en el mapa según su nivel.
     *
     * @param inputStream     Flujo de entrada del archivo de texto.
     * @param enemigos        Lista de enemigos disponibles para colocar en el mapa.
     * @param nivel           Nivel actual del juego (para ubicar enemigos de ese nivel).
     * @return Instancia del mapa construida a partir del archivo de texto.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Mapa cargarMapaDesdeTxt(InputStream inputStream, List<Enemigo> enemigos, int nivel)
            throws IOException {
        Mapa mapa = new Mapa();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linea;
        int alto = 0;
        int ancho = 0;
        List<Enemigo> enemigosColocados = new ArrayList<>();

        while ((linea = reader.readLine()) != null) {
            alto++;
            char[] caracteres = linea.toCharArray();
            ancho = caracteres.length;
            for (int i = 0; i < caracteres.length; i++) {
                switch (caracteres[i]) {
                    case '#':
                        mapa.colocarObstaculos(i, alto - 1, TipoObstaculo.PARED);
                        break;
                    case '.':
                        mapa.colocarCamino(i, alto - 1);
                        break;
                    case 'B':
                        mapa.colocarObstaculos(i, alto - 1, TipoObstaculo.BARRIL);
                        break;
                    case 'E':
                        mapa.colocarEntrada(i, alto - 1);
                        break;
                    case 'S':
                        mapa.colocarSalida(i, alto - 1);
                        break;
                    case 'C':
                        mapa.colocarObstaculos(i, alto - 1, TipoObstaculo.CHARCO);
                        break;
                    case 'O':
                        // Primero, coloca el suelo
                        mapa.colocarCamino(i, alto - 1);
                        // Luego, coloca el enemigo encima
                        for (Enemigo enemigo : enemigos) {
                            if (enemigo.getNivel() == nivel && !enemigosColocados.contains(enemigo)) {
                                enemigo.setX(i);
                                enemigo.setY(alto - 1);
                                mapa.colocarEnemigo(enemigo);
                                enemigosColocados.add(enemigo);
                                break;
                            }
                        }
                        break;
                    case 'I':
                        mapa.colocarCamino(i, alto - 1);
                        // Buscar un Troll en la lista de enemigos
                        for (Enemigo enemigo : enemigos) {
                            if (enemigo.getTipo() == TipoEnemigo.TROLL && !enemigosColocados.contains(enemigo)) {
                                enemigo.setX(i);
                                enemigo.setY(alto - 1);
                                mapa.colocarEnemigo(enemigo);
                                enemigosColocados.add(enemigo);
                                break;
                            }
                        }
                        break;
                    default:
                        System.out.println("Contenido desconocido en (" + i + ", " + (alto - 1) + "): " + caracteres[i]);
                        break;
                }
            }
        }

        mapa.setAlto(alto);
        mapa.setAncho(ancho);
        mapa.setEnemigos(enemigosColocados);
        return mapa;
    }
}
