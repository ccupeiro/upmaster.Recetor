package com.upvmaster.carlos.recetor.entities;

/**
 * Created by Carlos on 08/12/2016.
 */

public class Group {

    private int id;
    private String name_group;

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
        return "Group{" +
                "id=" + id +
                ", name_group='" + name_group + '\'' +
                '}';
    }
}
