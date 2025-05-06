package com.mazmorras.models;

import java.util.List;
import java.util.ArrayList;

import com.mazmorras.interfaces.JuegoObserver;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.skin.TextInputControlSkin.Direction;

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