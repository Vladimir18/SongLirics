package com.gramirez.songlirics.Model;

/**
 * Created by gilbertramirez on 2/14/17.
 */
public class MenuItem {
    private String nombre;
    private boolean selected;

    public MenuItem(String nombre, boolean selected) {
        this.nombre = nombre;
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
