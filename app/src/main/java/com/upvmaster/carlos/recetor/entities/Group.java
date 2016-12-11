package com.upvmaster.carlos.recetor.entities;

/**
 * Created by Carlos on 08/12/2016.
 */

public class Group {

    private int id;
    private String name_group;

    public Group(){

    }

    public Group(int id, String name_group) {
        this.id = id;
        this.name_group = name_group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_group() {
        return name_group;
    }

    public void setName_group(String name_group) {
        this.name_group = name_group;
    }

    @Override
    public String toString() {
        return name_group;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Group))return false;
        Group grupo_a_comparar = (Group)obj;
        if(this.id==grupo_a_comparar.getId())
            return true;
        return false;
    }
}
