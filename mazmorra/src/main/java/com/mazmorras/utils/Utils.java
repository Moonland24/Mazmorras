package com.mazmorras.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mazmorras.models.Enemigo;
import com.mazmorras.models.Mapa;
import com.mazmorras.models.TipoEnemigo;

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

    public static Mapa cargarMapaDesdeTxt(String path) throws IOException {
        Mapa mapa = new Mapa(); // Inicializa el mapa con dimensiones por defecto
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String linea;
        int alto = 0; // Inicializa la altura del mapa
        int ancho = 0; // Inicializa el ancho del mapa
        while ((linea = reader.readLine()) != null) {
            alto++; // Cuenta las líneas para establecer la altura
            char[] caracteres = linea.toCharArray();
            ancho = caracteres.length; // Establece el ancho del mapa
            for (int i = 0; i < caracteres.length; i++) {
                // Aquí puedes procesar cada carácter y construir el mapa
                // Por ejemplo, si 'X' es un obstáculo, puedes marcarlo en el mapa
                if (caracteres[i] == '#') {
                    mapa.colocarParedes(alto -1, i);// Marcar como pared

                } else if (caracteres[i] == '.') {
                    mapa.colocarCamino(alto -1, i);// Marcar como camino

                } else if (caracteres[i] == 'E') {
                    mapa.colocarEntrada(alto -1, i);

                } else if (caracteres[i] == 'S') {
                    mapa.colocarSalida(alto -1, i);
                }

            }
        }
        mapa.setAlto(alto); // Establece la altura del mapa
        mapa.setAncho(ancho); // Establece el ancho del mapa
        return mapa;                                                                                    // dimensiones del
                                                                                                   // mapa
        // Implementar la lógca para cargar el mapa desde un archivo de texto
        // y devolver una instancia de Mapa.
        // return null; // Placeholder, implementar correctamente
    }
}
