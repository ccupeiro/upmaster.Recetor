package com.upvmaster.carlos.recetor.entities;

/**
 * Created by Carlos on 10/12/2016.
 */

public class Variante {
    private int id;
    private String description;

    public Variante() {
        this.id = -1;
    }

    public Variante(String description) {
        this.id = -1;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Variante{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
