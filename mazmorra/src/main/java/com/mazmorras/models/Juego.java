package com.mazmorras.models;

import java.util.List;
import java.util.ArrayList;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

//La clase Juego} implementa la interfaz y representa
//un modelo de juego que permite a los oyentes ser notificados de cambios.
//Esta clase mantiene una lista de objetos que pueden
//ser añadidos o eliminados para observar cambios en el estado del juego.
//addListener(InvalidationListener)}: Añade un oyente a la lista si no es nulo y no está ya presente.
//removeListener(InvalidationListener)}: Elimina un oyente de la lista.
//getTurnoActual: Recupera el turno actual (nota: este método puede tener un problema de recursión infinita).
//El método getTurnoActual parece llamarse a sí mismo recursivamente, lo que puede llevar a un StackOverflowError. 
//Esto debería ser revisado y corregido.
public class Juego implements Observable {

    private List<InvalidationListener> listeners;

    public Juego() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

    public Object getTurnoActual() {
        return this.getTurnoActual();
    }
}