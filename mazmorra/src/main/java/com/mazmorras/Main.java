package com.mazmorras;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.parser.ParseException;

import com.mazmorras.models.Enemigo;
import com.mazmorras.models.Mapa;
import com.mazmorras.utils.Utils;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Utils utils = new Utils();
        List<Enemigo> enemigosNivel1 = new ArrayList<>();
        List<Enemigo> enemigos = utils.cargarDesdeJSON(
                "C:\\Users\\jfco1\\Desktop\\Mazmorras\\mazmorra\\src\\main\\resources\\enemigos\\enemigos.json");
        for (Enemigo enemigo : enemigos) {
            if (enemigo.getNivel() == 1) {
                enemigosNivel1.add(enemigo);

            }
        }
        for (Enemigo enemigo2 : enemigosNivel1) {
            System.out.println("Enemigo Nivel 1: " + enemigo2.toString());
        }
        Random random = new Random();
        int randomIndex = random.nextInt(enemigosNivel1.size());
        Enemigo enemigoSeleccionado = enemigosNivel1.get(randomIndex);
        // enemigoSeleccionado.mover(null, enemigoSeleccionado);
        Mapa mapa = new Mapa(10, 10);
        mapa.colocarEnemigo(enemigoSeleccionado);
        System.out.println("Enemigo seleccionado: " + enemigoSeleccionado.toString());
    }
}
