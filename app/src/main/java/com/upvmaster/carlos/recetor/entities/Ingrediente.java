package com.upvmaster.carlos.recetor.entities;

/**
 * Created by Carlos on 03/12/2016.
 */
public class Ingrediente {
    private String name;
    private double cantidad;

    public Ingrediente() {
    }

    public Ingrediente(String name, double cantidad) {
        this.name = name;
        this.cantidad = cantidad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "name='" + name + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
