package com.upvmaster.carlos.recetor.bbdd.dao;

import android.database.sqlite.SQLiteDatabase;

import com.upvmaster.carlos.recetor.entities.Ingrediente;
import com.upvmaster.carlos.recetor.entities.Receipt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 08/12/2016.
 */

public class ReceiptDao {

    private SQLiteDatabase db;

    public ReceiptDao(SQLiteDatabase db){
        this.db = db;
    }

    public void insert(Receipt receta){
        //insert
    }

    public List<Receipt> getAlphList() throws Exception{
        List<Receipt> lista = new ArrayList<Receipt>();
        Receipt r = new Receipt();
        r.setName("Alcachofas");
        r.setGroup(0);
        List<Ingrediente> lista_ing = new ArrayList<Ingrediente>();
        Ingrediente i = new Ingrediente();
        i.setName("Alcachofa");
        i.setCantidad(4);
        lista_ing.add(i);
        i = new Ingrediente();
        i.setName("Sal");
        i.setCantidad(0.5);
        lista_ing.add(i);
        r.setList_ingredients(lista_ing);
        List<String> lista_pasos = new ArrayList<String>();
        lista_pasos.add("Cocer las alcachofas");
        lista_pasos.add("Echar sal a las alcachofas");
        lista_pasos.add("Servir las alcachofas");
        r.setList_steps(lista_pasos);
        lista.add(r);
        r = new Receipt();
        r.setName("Arroz a la Cubana");
        lista.add(r);
        r = new Receipt();
        r.setName("Brownie");
        lista.add(r);
        r = new Receipt();
        r.setName("Canelones");
        lista.add(r);
        r = new Receipt();
        r.setName("Pizza");
        lista.add(r);
        r = new Receipt();
        r.setName("Puré Calabacín");
        lista.add(r);
        return lista;
    }
    public List<Receipt> getGroupList(){
        List<Receipt> lista = new ArrayList<Receipt>();
        Receipt r = new Receipt();
        r.setName("Canelones");
        lista.add(r);
        r = new Receipt();
        r.setName("Pizza");
        lista.add(r);
        r = new Receipt();
        r.setName("Alcachofas");
        lista.add(r);
        r = new Receipt();
        r.setName("Brownie");
        lista.add(r);
        r = new Receipt();
        r.setName("Arroz a la Cubana");
        lista.add(r);
        r = new Receipt();
        r.setName("Puré Calabacín");
        lista.add(r);
        return lista;
    }
    public void delete(int idReceipt){

    }
    public void update(Receipt receta){

    }

}
