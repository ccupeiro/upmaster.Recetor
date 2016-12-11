package com.upvmaster.carlos.recetor.entities;

import java.util.List;

/**
 * Created by Carlos on 03/12/2016.
 */

public class Receipt {
    private String name;
    private String src_photo;
    private int group;
    private int id;
    private List<Ingrediente> list_ingredients;
    private List<Step> list_steps;
    private List<Variante> list_variantes;

    public Receipt() {
        group = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc_photo() {
        return src_photo;
    }

    public void setSrc_photo(String src_photo) {
        this.src_photo = src_photo;
    }

    public List<Ingrediente> getList_ingredients() {
        return list_ingredients;
    }

    public void setList_ingredients(List<Ingrediente> list_ingredients) {
        this.list_ingredients = list_ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Step> getList_steps() {
        return list_steps;
    }

    public void setList_steps(List<Step> list_steps) {
        this.list_steps = list_steps;
    }

    public List<Variante> getList_variantes() {
        return list_variantes;
    }

    public void setList_variantes(List<Variante> list_variantes) {
        this.list_variantes = list_variantes;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "name='" + name + '\'' +
                ", src_photo='" + src_photo + '\'' +
                ", group=" + group +
                ", id=" + id +
                ", list_ingredients=" + list_ingredients +
                ", list_steps=" + list_steps +
                ", list_variantes=" + list_variantes +
                '}';
    }
}
