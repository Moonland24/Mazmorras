package com.mazmorras.models;

import java.util.List;
import java.util.ArrayList;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

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