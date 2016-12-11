package com.upvmaster.carlos.recetor.entities;

/**
 * Created by Carlos on 10/12/2016.
 */

public class Step {
    private int id;
    private int pos_paso;
    private String description;

    public Step() {
        this.id = -1;
    }

    public Step(int pos_paso, String description) {
        this.id = -1;
        this.pos_paso = pos_paso;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos_paso() {
        return pos_paso;
    }

    public void setPos_paso(int pos_paso) {
        this.pos_paso = pos_paso;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", pos_paso=" + pos_paso +
                ", description='" + description + '\'' +
                '}';
    }
}
