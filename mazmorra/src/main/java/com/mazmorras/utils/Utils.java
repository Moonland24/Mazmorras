package com.mazmorras.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mazmorras.models.Enemigo;
import com.mazmorras.models.TipoEnemigo;


public class Utils {
    public List<Enemigo> cargarDesdeJSON(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(path);
        Object obj = parser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray array = (JSONArray)jsonObject.get("enemigos");
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
            Enemigo enemigo = new Enemigo(nombre, 0, 0, vidaBase, ataqueBase, defensaBase, velocidadBase, TipoEnemigo.valueOf(tipo), percepcionBase, nivelBase);
            auxiliar.add(enemigo);
        }
        return auxiliar;
    }

}
