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

public class Utils {
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
        return auxiliar;
    }

    public static Mapa cargarMapaDesdeTxt(InputStream inputStream) throws IOException {
        Mapa mapa = new Mapa(); // Inicializa el mapa
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linea;
        int alto = 0; // Inicializa la altura del mapa
        int ancho = 0; // Inicializa el ancho del mapa
        List<Enemigo> enemigos = new ArrayList<>(); // Lista para almacenar enemigos

        while ((linea = reader.readLine()) != null) {
            alto++; // Cuenta las líneas para establecer la altura
            char[] caracteres = linea.toCharArray();
            ancho = caracteres.length; // Establece el ancho del mapa
            for (int i = 0; i < caracteres.length; i++) {
                // Procesar cada carácter y construir el mapa
                switch (caracteres[i]) {
                    case '#':
                        mapa.colocarObstaculos(alto - 1, i, TipoObstaculo.PARED); // Marcar como pared
                        break;
                    case '.':
                        mapa.colocarCamino(alto - 1, i); // Marcar como camino
                        break;
                    case 'B':
                        mapa.colocarObstaculos(alto - 1, i, TipoObstaculo.BARRIL); // Marcar como barril
                        break;
                    case 'E':
                        mapa.colocarEntrada(alto - 1, i); // Marcar como entrada
                        break;
                    case 'S':
                        mapa.colocarSalida(alto - 1, i); // Marcar como salida
                        break;
                    case 'C':
                        mapa.colocarObstaculos(alto - 1, i, TipoObstaculo.CHARCO); // Marcar como charco
                        break;
                    case 'O':
                        // Crear un enemigo genérico y agregarlo a la lista
                        Enemigo enemigo = new Enemigo("Enemigo", alto - 1, i, 20, 5, 3, 2, TipoEnemigo.ESQUELETO, 3, 1);
                        enemigos.add(enemigo);
                        break;
                    default:
                        System.out.println("Contenido desconocido en (" + (alto - 1) + ", " + i + "): " + caracteres[i]);
                        break;
                }
            }
        }

        mapa.setAlto(alto); // Establece la altura del mapa
        mapa.setAncho(ancho); // Establece el ancho del mapa
        mapa.setEnemigos(enemigos); // Asigna la lista de enemigos al mapa
        return mapa;
    }
}
