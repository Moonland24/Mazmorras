package com.mazmorras.controllers;

import com.mazmorras.models.Juego;
import com.mazmorras.views.JuegoView;

import javafx.application.Platform;
import javafx.scene.control.skin.TextInputControlSkin.Direction;

public class JuegoController {

    private Juego juego;
    private JuegoView view;
    

    //Utils -- Leyendo enemigos
    // recorro la lista de enemigos que me devuelve el utils
    // si es el primer nivel, un enemigo, sacas solo los enemigos de nivel 1

//     for(int i = 0; i < enemigos.size(); i++){
//         Enemigo enemigo = enemigos.get(i);
//         // filtrar los enemigos por nivel
//         if(enemigo.getNivel() == 1){
//             // añadir el enemigo a la lista de enemigos del juego
//             juego.addEnemigo(enemigo);
//         }
// }
}