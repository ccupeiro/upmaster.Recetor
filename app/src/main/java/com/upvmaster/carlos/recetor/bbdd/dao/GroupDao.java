package com.upvmaster.carlos.recetor.bbdd.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.upvmaster.carlos.recetor.bbdd.TGroups;
import com.upvmaster.carlos.recetor.bbdd.TReceipt;
import com.upvmaster.carlos.recetor.entities.Group;
import com.upvmaster.carlos.recetor.entities.Ingrediente;
import com.upvmaster.carlos.recetor.entities.Receipt;

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

    public String getName(int id){
        Cursor c = null;
        try {
            c = db.rawQuery(TGroups.SELECT_NAME,new String[]{String.valueOf(id)});
            if(c.moveToFirst()){
                String nombre = c.getString(c.getColumnIndex(TGroups.DESCRIPTION));
                return nombre;
            }
        } catch (SQLiteException e) {
            throw e;
        } finally {
            c.close();
        }
        return null;
    }

    public List<Group> getAllGroups(){
        List<Group> lista_grupos = new ArrayList<Group>();
        /*lista_grupos.add(new Group(0,"Aperitivos y Entrantes"));
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
        lista_grupos.add(new Group(11,"Bebidas"));*/
        Cursor c = null;
        try {
            c = db.rawQuery(TGroups.SELECT_ALL,null);
            if(c.moveToFirst()){
                do{
                    Group g = new Group();
                    g.setId(c.getInt(c.getColumnIndex(TGroups.ID)));
                    g.setName_group(c.getString(c.getColumnIndex(TGroups.DESCRIPTION)));
                    lista_grupos.add(g);
                }while(c.moveToNext());
            }
        } catch (SQLiteException e) {
            throw e;
        } finally {
            c.close();
        }
        return lista_grupos;
    }
}
