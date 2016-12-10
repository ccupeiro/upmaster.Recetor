package com.upvmaster.carlos.recetor.bbdd.dao;

import android.database.sqlite.SQLiteDatabase;

import com.upvmaster.carlos.recetor.entities.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 08/12/2016.
 */

public class GroupDao {
    private SQLiteDatabase db;

    public GroupDao(SQLiteDatabase db){
        this.db = db;
    }

    public void insert(String name_group){

    }

    public String getName(int id){
        return null;
    }

    public void delete(int id){

    }
    public List<Group> getAllGroups(){
        List<Group> lista_grupos = new ArrayList<Group>();
        lista_grupos.add(new Group(0,"Aperitivos y Entrantes"));
        lista_grupos.add(new Group(1,"Sopas"));
        lista_grupos.add(new Group(2,"Pastas y Arroces"));
        lista_grupos.add(new Group(3,"Platos principales"));
        lista_grupos.add(new Group(4,"Acompañamientos"));
        lista_grupos.add(new Group(5,"Empanadas, Quiches y Pizzas"));
        lista_grupos.add(new Group(6,"Pan y Bollería"));
        lista_grupos.add(new Group(7,"Salsas, DIPS y Patés"));
        lista_grupos.add(new Group(8,"Salsas y cremas dulces, mermeladas"));
        lista_grupos.add(new Group(9,"Postres y dulces"));
        lista_grupos.add(new Group(10,"Repostería y pastelería"));
        lista_grupos.add(new Group(11,"Bebidas"));
        return null;
    }
}
