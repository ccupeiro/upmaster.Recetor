package com.upvmaster.carlos.recetor.entities;

import java.io.Serializable;

/**
 * Created by Carlos on 03/12/2016.
 */
public class Ingrediente implements Serializable {
    private int id;
    private String name;
    private String cantidad;

    public Ingrediente() {
        id = -1;
    }

    public Ingrediente(String name, String cantidad) {
        id=-1;
        this.name = name;
        this.cantidad = cantidad;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
